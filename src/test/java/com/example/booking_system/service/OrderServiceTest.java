package com.example.booking_system.service;

import com.example.booking_system.dto.CreateOrderRequestDto;
import com.example.booking_system.dto.OrderResponseDto;
import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.enums.Status;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.Event;
import com.example.booking_system.model.Order;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.model.User;
import com.example.booking_system.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    @Mock
    private TicketService ticketService;
    @Mock
    private EventService eventService;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Ticket ticket;
    private Event event;
    private UUID userId;
    private UUID ticketId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        ticketId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        event = new Event();
        event.setId(UUID.randomUUID());
        event.setAvailableTickets(10);

        ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setStatus(Status.AVAILABLE);
        ticket.setEvent(event);
        ticket.setPrice(BigDecimal.valueOf(50.0));
    }

    @Test
    void createOrder_createsAndSavesOrder() {
        // Arrange request
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketId(ticketId);

        CreateOrderRequestDto request = new CreateOrderRequestDto();
        request.setUserId(userId);
        request.setTotalAmount(BigDecimal.valueOf(50));
        request.setTickets(List.of(ticketDto));

        when(userService.getUserById(userId)).thenReturn(user);
        when(ticketService.getAvailableTicket(ticketId)).thenReturn(ticket);
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            o.setId(UUID.randomUUID());
            return o;
        });

        // Act
        OrderResponseDto response = orderService.createOrder(request);

        // Assert
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(1, response.getTickets().size());
        assertEquals(Status.BOOKED, ticket.getStatus());
        assertEquals(9, event.getAvailableTickets()); // decremented

        // Verify interactions
        verify(userService).getUserById(userId);
        verify(ticketService).getAvailableTicket(ticketId);
        verify(ticketService).saveTicket(ticket);
        verify(eventService).saveEvent(event);
        verify(orderRepository).save(any(Order.class));

        // Capture order to ensure ticket is linked
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertTrue(orderCaptor.getValue().getTickets().contains(ticket));
    }

    @Test
    void getOrderForUser_returnsOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUser(user);
        order.setBookingTime(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.TEN);

        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(order));

        OrderResponseDto dto = orderService.getOrderForUser(userId);

        assertNotNull(dto);
        assertEquals(order.getId(), dto.getOrderId());
        verify(orderRepository).findByUserId(userId);
    }

    @Test
    void getOrderForUser_notFound_throws() {
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.getOrderForUser(userId));

        verify(orderRepository).findByUserId(userId);
    }
}
