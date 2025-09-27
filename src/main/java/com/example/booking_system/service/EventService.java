package com.example.booking_system.service;

import com.example.booking_system.dto.CreateEventRequestDto;
import com.example.booking_system.model.Event;
import java.util.List;
import java.util.UUID;

public interface EventService {
    List<Event> getAllEvent();
    Event getEventById(UUID id);
    void saveEvent(Event event);
    String createEvent(CreateEventRequestDto request);
}
