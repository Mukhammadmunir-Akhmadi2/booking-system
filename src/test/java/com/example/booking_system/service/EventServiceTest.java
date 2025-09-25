package com.example.booking_system.service;

import com.example.booking_system.dto.CreateEventRequestDto;
import com.example.booking_system.dto.CreateTicketDto;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.Event;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private EventService eventService;

    private Event sampleEvent;
    private UUID eventId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventId = UUID.randomUUID();

        sampleEvent = new Event();
        sampleEvent.setId(eventId);
        sampleEvent.setName("Rock Night");
        sampleEvent.setVenue("City Arena");
        sampleEvent.setDateTime(LocalDateTime.now());
        sampleEvent.setTotalTickets(100);
        sampleEvent.setAvailableTickets(100);
    }

    @Test
    void getAllEvent_returnsList() {
        when(eventRepository.findAll()).thenReturn(List.of(sampleEvent));

        List<Event> events = eventService.getAllEvent();

        assertEquals(1, events.size());
        assertEquals("Rock Night", events.get(0).getName());
        verify(eventRepository).findAll();
    }

    @Test
    void getEventById_found() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(sampleEvent));

        Event found = eventService.getEventById(eventId);

        assertNotNull(found);
        assertEquals(eventId, found.getId());
        verify(eventRepository).findById(eventId);
    }

    @Test
    void getEventById_notFound_throwsException() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEventById(eventId));
        verify(eventRepository).findById(eventId);
    }

    @Test
    void saveEvent_success() {
        when(eventRepository.save(sampleEvent)).thenReturn(sampleEvent);

        eventService.saveEvent(sampleEvent);

        verify(eventRepository).save(sampleEvent);
    }

    @Test
    void createEvent_success() {
        // Arrange DTO
        CreateTicketDto ticketDto = new CreateTicketDto();
        ticketDto.setSector("A");
        ticketDto.setRowNumber(1);
        ticketDto.setSeatNumber(1);
        ticketDto.setPrice(BigDecimal.valueOf(50.0));

        CreateEventRequestDto request = new CreateEventRequestDto();
        request.setName("Jazz Night");
        request.setVenue("Blue Hall");
        request.setDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        request.setTotalTickets(50);
        request.setTickets(List.of(ticketDto));

        Event savedEvent = new Event();
        savedEvent.setId(UUID.randomUUID());
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // Act
        String resultId = eventService.createEvent(request);

        // Assert
        assertNotNull(resultId);
        // verify eventRepository.save called once
        verify(eventRepository, times(1)).save(any(Event.class));
        // verify ticketService.saveTicket called for each ticket
        verify(ticketService, times(1)).saveTicket(any(Ticket.class));

        // capture the event passed to repository
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventCaptor.capture());
        Event captured = eventCaptor.getValue();
        assertEquals("Jazz Night", captured.getName());
        assertEquals(50, captured.getTotalTickets());
    }
}