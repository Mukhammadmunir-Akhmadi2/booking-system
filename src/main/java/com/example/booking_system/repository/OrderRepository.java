package com.example.booking_system.repository;

import com.example.booking_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    Optional<Order> findByUserId(@Param("userId") UUID userId);
}
