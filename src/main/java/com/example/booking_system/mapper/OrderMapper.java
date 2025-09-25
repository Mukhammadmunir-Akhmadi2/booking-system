package com.example.booking_system.mapper;

import com.example.booking_system.dto.OrderResponseDto;
import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setBookingTime(order.getBookingTime().toString()); // ISO-8601 string
        dto.setTotalAmount(order.getTotalAmount());

        List<TicketDto> ticketDtos = order.getTickets()
                .stream()
                .map(TicketMapper::toTicketDto)
                .collect(Collectors.toList());

        dto.setTickets(ticketDtos);

        return dto;
    }
}
