package com.example.booking_system.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EventDto {
    private UUID eventId;
    private String name;
    private String dateTime;
    private String venue;
    private int totalTickets;
    private int availableTickets;
}
