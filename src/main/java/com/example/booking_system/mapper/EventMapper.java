package com.example.booking_system.mapper;

import com.example.booking_system.dto.EventDto;
import com.example.booking_system.model.Event;
import com.example.booking_system.utils.DateTimeUtils;

public class EventMapper {
    public static EventDto toDto(Event event) {
        if (event == null) return null;

        EventDto dto = new EventDto();
        dto.setId(event.getId() != null ? event.getId().toString() : null);
        dto.setName(event.getName());
        dto.setDateTime(DateTimeUtils.toString(event.getDateTime()));
        dto.setVenue(event.getVenue());
        dto.setTotalTickets(event.getTotalTickets());
        dto.setAvailableTickets(event.getAvailableTickets());
        return dto;
    }
}
