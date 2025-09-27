package com.example.booking_system.service;

import com.example.booking_system.model.Ticket;
import java.util.List;
import java.util.UUID;

public interface TicketService {
    Ticket saveTicket(Ticket ticket);
    List<Ticket> GetAllTickets();
    Ticket getAvailableTicket(UUID id);
    List<Ticket> getTicketByEventId(UUID eventId);
}
