package com.example.booking_system.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponseDto {
    private UUID orderId;
    private UUID userId;
    private List<TicketDto> tickets;
    private String bookingTime;
    private BigDecimal totalAmount;
}
