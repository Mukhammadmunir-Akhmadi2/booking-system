package com.example.booking_system.service.impl;

import com.example.booking_system.dto.CreateOrderRequestDto;
import com.example.booking_system.dto.OrderResponseDto;
import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.enums.Status;
import com.example.booking_system.exceptions.DuplicationException;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.mapper.OrderMapper;
import com.example.booking_system.model.Event;
import com.example.booking_system.model.Order;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.model.User;
import com.example.booking_system.repository.OrderRepository;
import com.example.booking_system.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    private final TicketServiceImpl ticketService;
    private final EventServiceImpl eventService;

    @Override
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {
        User currentUser = userService.getUserById(request.getUserId());
        if (currentUser.getOrder() != null) {
            throw new DuplicationException("Duplicate order: this user has an existing order.");

        }
        Order order = new Order();
        order.setTotalAmount(request.getTotalAmount());
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

    @Override
    public OrderResponseDto getOrderForUser(UUID userId) {
        return OrderMapper.toDto(orderRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", userId.toString()))
        );
    }
}
