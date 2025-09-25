package com.example.booking_system.repository;

import com.example.booking_system.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventRepositoryTest {

    @Mock
    private EventRepository eventRepository;

    private Event sampleEvent;
    private UUID eventId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventId = UUID.randomUUID();

        sampleEvent = new Event();
        sampleEvent.setId(eventId);
        sampleEvent.setName("Spring Concert");
        sampleEvent.setDateTime(LocalDateTime.now().plusDays(10));
        sampleEvent.setVenue("Grand Hall");
        sampleEvent.setTotalTickets(100);
        sampleEvent.setAvailableTickets(100);
    }

    @Test
    void saveEvent_success() {
        when(eventRepository.save(sampleEvent)).thenReturn(sampleEvent);

        Event saved = eventRepository.save(sampleEvent);

        assertNotNull(saved);
        assertEquals("Spring Concert", saved.getName());
        verify(eventRepository).save(sampleEvent);
    }

    @Test
    void findById_returnsEvent() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(sampleEvent));

        Optional<Event> result = eventRepository.findById(eventId);

        assertTrue(result.isPresent());
        assertEquals(eventId, result.get().getId());
        verify(eventRepository).findById(eventId);
    }

    @Test
    void findAll_returnsList() {
        when(eventRepository.findAll()).thenReturn(List.of(sampleEvent));

        List<Event> events = eventRepository.findAll();

        assertEquals(1, events.size());
        assertEquals(eventId, events.get(0).getId());
        verify(eventRepository).findAll();
    }

    @Test
    void deleteById_success() {
        doNothing().when(eventRepository).deleteById(eventId);

        eventRepository.deleteById(eventId);

        verify(eventRepository).deleteById(eventId);
    }
}
