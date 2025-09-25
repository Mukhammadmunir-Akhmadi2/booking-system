package com.example.booking_system.repository;

import com.example.booking_system.enums.Status;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketRepositoryTest {

    @Mock
    private TicketRepository ticketRepository;

    private UUID ticketId;
    private UUID eventId;
    private Ticket sampleTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketId = UUID.randomUUID();
        eventId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);

        sampleTicket = new Ticket();
        sampleTicket.setId(ticketId);
        sampleTicket.setEvent(event);
        sampleTicket.setStatus(Status.AVAILABLE);
    }

    @Test
    void findAvailableById_returnsTicket_whenAvailable() {
        when(ticketRepository.findAvailableById(ticketId))
                .thenReturn(Optional.of(sampleTicket));

        Optional<Ticket> result = ticketRepository.findAvailableById(ticketId);

        assertTrue(result.isPresent());
        assertEquals(ticketId, result.get().getId());
        verify(ticketRepository).findAvailableById(ticketId);
    }

    @Test
    void findAvailableById_returnsEmpty_whenNotAvailable() {
        when(ticketRepository.findAvailableById(ticketId))
                .thenReturn(Optional.empty());

        Optional<Ticket> result = ticketRepository.findAvailableById(ticketId);

        assertTrue(result.isEmpty());
        verify(ticketRepository).findAvailableById(ticketId);
    }

    @Test
    void findAvailableByEventId_returnsList() {
        when(ticketRepository.findAvailableByEventId(eventId))
                .thenReturn(List.of(sampleTicket));

        List<Ticket> tickets = ticketRepository.findAvailableByEventId(eventId);

        assertEquals(1, tickets.size());
        assertEquals(ticketId, tickets.get(0).getId());
        verify(ticketRepository).findAvailableByEventId(eventId);
    }
}
