package com.example.booking_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private String venue;
    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;
    @Column(name = "available_tickets", nullable = false)
    private int availableTickets;
}
