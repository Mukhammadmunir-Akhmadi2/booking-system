package com.example.booking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Order order;
}