package com.example.booking_system.repository;

import com.example.booking_system.model.Order;
import com.example.booking_system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    private UUID userId;
    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        sampleOrder = new Order();
        sampleOrder.setId(UUID.randomUUID());
        sampleOrder.setUser(user);
    }

    @Test
    void findByUserId_returnsOrder() {
        when(orderRepository.findByUserId(userId))
                .thenReturn(Optional.of(sampleOrder));

        Optional<Order> result = orderRepository.findByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getUser().getId());
        verify(orderRepository).findByUserId(userId);
    }

    @Test
    void findByUserId_returnsEmpty_whenNoOrder() {
        when(orderRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        Optional<Order> result = orderRepository.findByUserId(userId);

        assertTrue(result.isEmpty());
        verify(orderRepository).findByUserId(userId);
    }
}
