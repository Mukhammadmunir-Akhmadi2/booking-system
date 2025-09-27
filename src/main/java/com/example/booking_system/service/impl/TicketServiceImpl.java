package com.example.booking_system.service.impl;

import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.repository.TicketRepository;
import com.example.booking_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> GetAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getAvailableTicket(UUID id) {
        return ticketRepository.findAvailableById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id.toString()));
    }

    @Override
    public List<Ticket> getTicketByEventId(UUID eventId) {
        List<Ticket> tickets = ticketRepository.findAvailableByEventId(eventId);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("Ticket", "event_id", eventId.toString());
        }
        return tickets;
    }
}
