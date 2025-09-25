package com.example.booking_system.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TicketDto {
    private UUID ticketId;
    private int rowNumber;
    private int seatNumber;
    private String sector;
    private BigDecimal price;
    private UUID eventId;
}
