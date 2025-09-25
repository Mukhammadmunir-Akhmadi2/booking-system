package com.example.booking_system.service;

import com.example.booking_system.dto.CreateEventRequest;
import com.example.booking_system.dto.CreateTicketDto;
import com.example.booking_system.enums.Status;
import com.example.booking_system.model.Event;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.repository.EventRepository;
import com.example.booking_system.repository.TicketRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.booking_system.utils.DateTimeUtils.toLocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {
    private EventRepository eventRepository;
    private TicketRepository ticketRepository;

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }


    public String createEvent(CreateEventRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDateTime(toLocalDateTime(request.getDateTime()));
        event.setVenue(request.getVenue());
        event.setTotalTickets(request.getTotalTickets());
        event.setAvailableTickets(request.getTotalTickets());

        for(CreateTicketDto ticketDto : request.getTickets()) {
            Ticket ticket = new Ticket();
            ticket.setSector(ticketDto.getSector());
            ticket.setRowNumber(ticketDto.getRowNumber());
            ticket.setSeatNumber(ticketDto.getSeatNumber());
            ticket.setDateTime(event.getDateTime());
            ticket.setStatus(Status.AVAILABLE);
            ticket.setPrice(ticketDto.getPrice());
            ticket.setEvent(event);
        }

        return eventRepository.save(event)
                .getId()
                .toString();
    }
}
