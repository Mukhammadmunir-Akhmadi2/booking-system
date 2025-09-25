package com.example.booking_system.service;

import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> GetAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getAvailableTicket(UUID id) {
        return ticketRepository.findAvailableById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id.toString()));
    }
}
