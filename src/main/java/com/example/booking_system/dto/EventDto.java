package com.example.booking_system.dto;

import lombok.Data;

@Data
public class EventDto {
    private String id;
    private String name;
    private String dateTime;
    private String venue;
    private int totalTickets;
    private int availableTickets;
}
