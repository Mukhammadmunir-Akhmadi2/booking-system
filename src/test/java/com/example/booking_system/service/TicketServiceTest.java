package com.example.booking_system.service;

import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.repository.TicketRepository;
import com.example.booking_system.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTicket_shouldReturnSavedTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket saved = ticketService.saveTicket(ticket);

        assertNotNull(saved);
        assertEquals(ticket.getId(), saved.getId());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void getAllTickets_shouldReturnList() {
        List<Ticket> mockList = List.of(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(mockList);

        List<Ticket> result = ticketService.GetAllTickets();

        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void getAvailableTicket_shouldReturnTicket_whenFound() {
        UUID id = UUID.randomUUID();
        Ticket mockTicket = new Ticket();
        mockTicket.setId(id);
        when(ticketRepository.findAvailableById(id)).thenReturn(Optional.of(mockTicket));

        Ticket result = ticketService.getAvailableTicket(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(ticketRepository, times(1)).findAvailableById(id);
    }

    @Test
    void getAvailableTicket_shouldThrow_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(ticketRepository.findAvailableById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> ticketService.getAvailableTicket(id));
        verify(ticketRepository, times(1)).findAvailableById(id);
    }

    @Test
    void getTicketByEventId_shouldReturnTickets_whenFound() {
        UUID eventId = UUID.randomUUID();
        List<Ticket> tickets = List.of(new Ticket(), new Ticket());
        when(ticketRepository.findAvailableByEventId(eventId)).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketByEventId(eventId);

        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findAvailableByEventId(eventId);
    }

    @Test
    void getTicketByEventId_shouldThrow_whenEmpty() {
        UUID eventId = UUID.randomUUID();
        when(ticketRepository.findAvailableByEventId(eventId)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> ticketService.getTicketByEventId(eventId));
        verify(ticketRepository, times(1)).findAvailableByEventId(eventId);
    }
}
