package com.example.booking_system.service.impl;

import com.example.booking_system.dto.CreateEventRequestDto;
import com.example.booking_system.dto.CreateTicketDto;
import com.example.booking_system.enums.Status;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.Event;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.repository.EventRepository;
import com.example.booking_system.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import static com.example.booking_system.utils.DateTimeUtils.toLocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final TicketServiceImpl ticketService;

    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    }

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }


    @Override
    @Transactional
    public String createEvent(CreateEventRequestDto request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDateTime(toLocalDateTime(request.getDateTime()));
        event.setVenue(request.getVenue());
        event.setTotalTickets(request.getTotalTickets());
        event.setAvailableTickets(request.getTotalTickets());

        Event savedEvent = eventRepository.save(event);

        for(CreateTicketDto ticketDto : request.getTickets()) {
            Ticket ticket = new Ticket();
            ticket.setSector(ticketDto.getSector());
            ticket.setRowNumber(ticketDto.getRowNumber());
            ticket.setSeatNumber(ticketDto.getSeatNumber());
            ticket.setDateTime(event.getDateTime());
            ticket.setStatus(Status.AVAILABLE);
            ticket.setPrice(ticketDto.getPrice());
            ticket.setEvent(event);

            ticketService.saveTicket(ticket);
        }

        return savedEvent.getId()
                .toString();
    }
}
