package com.example.booking_system.service;

import com.example.booking_system.dto.CreateOrderRequestDto;
import com.example.booking_system.dto.OrderResponseDto;

import java.util.UUID;

public interface OrderService {
    OrderResponseDto createOrder(CreateOrderRequestDto request);
    OrderResponseDto getOrderForUser(UUID userId);
}
