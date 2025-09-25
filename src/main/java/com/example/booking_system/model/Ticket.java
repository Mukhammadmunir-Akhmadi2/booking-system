package com.example.booking_system.model;

import com.example.booking_system.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"event_id", "sector", "row_number", "seat_number"})
})
@Data
public class Ticket {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String sector;
    @Column(name = "row_number", nullable = false)
    private int rowNumber;
    @Column(name = "seat_number", nullable = false)
    private int seatNumber;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
