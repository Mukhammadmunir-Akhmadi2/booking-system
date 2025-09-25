package com.example.booking_system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();
}