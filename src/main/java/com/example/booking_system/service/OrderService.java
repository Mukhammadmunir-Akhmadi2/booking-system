package com.example.booking_system.service;

import com.example.booking_system.dto.CreateOrderRequestDto;
import com.example.booking_system.dto.OrderResponseDto;
import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.enums.Status;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.mapper.OrderMapper;
import com.example.booking_system.model.Event;
import com.example.booking_system.model.Order;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.model.User;
import com.example.booking_system.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final TicketService ticketService;
    private final EventService eventService;

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {
        User currentUser = userService.getUserById(request.getUserId());
        Order order;
        if (currentUser.getOrder() == null) {
            order = new Order();
            order.setTotalAmount(request.getTotalAmount());

        } else {
            order = currentUser.getOrder();
            order.setTotalAmount(order.getTotalAmount().add(request.getTotalAmount()));
        }

        order.setUser(currentUser);
        order.setBookingTime(LocalDateTime.now());

        for(TicketDto ticketDto : request.getTickets()) {
            Ticket ticket = ticketService.getAvailableTicket(ticketDto.getTicketId());
            ticket.setStatus(Status.BOOKED);
            ticket.setOrder(order);
            ticketService.saveTicket(ticket);

            Event event = ticket.getEvent();
            event.setAvailableTickets(event.getAvailableTickets() - 1);
            eventService.saveEvent(event);

            order.getTickets().add(ticket);
        }

        Order savedOrder = orderRepository.save(order);

        currentUser.setOrder(savedOrder);
        return OrderMapper.toDto(savedOrder);
    }

    public OrderResponseDto getOrderForUser(UUID userId) {
        return OrderMapper.toDto(orderRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", userId.toString()))
        );
    }
}
