package com.example.booking_system.mapper;

import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.model.Ticket;

public class TicketMapper {

    public static TicketDto toTicketDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setTicketId(ticket.getId());
        dto.setSector(ticket.getSector());
        dto.setEventId(ticket.getEvent().getId());
        dto.setRowNumber(ticket.getRowNumber());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPrice(ticket.getPrice());
        return dto;
    }
}
