package com.example.booking_system.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CreateOrderRequestDto {
    @NotNull
    private UUID userId;
    @NotEmpty
    private List<TicketDto> tickets;
    @NotNull
    private BigDecimal totalAmount;
}
