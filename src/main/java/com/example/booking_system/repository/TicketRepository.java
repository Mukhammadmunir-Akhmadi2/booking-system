package com.example.booking_system.repository;

import com.example.booking_system.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query("SELECT t FROM Ticket t WHERE t.id = :id AND t.status = 'AVAILABLE'")
    Optional<Ticket> findAvailableById(@Param("id") UUID id);


    @Query("SELECT t FROM Ticket t WHERE t.event.id = :eventId AND t.status = 'AVAILABLE'")
    List<Ticket> findAvailableByEventId(@Param("eventId")UUID eventId);
}
