package com.example.booking_system.controller;

import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.mapper.TicketMapper;
import com.example.booking_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/tickets/{eventId}")
    public ResponseEntity<List<TicketDto>> getTicketsByEventId(@PathVariable("eventId") UUID eventId) {
        return ResponseEntity.ok(ticketService.getTicketByEventId(eventId)
                .stream()
                .map(TicketMapper::toTicketDto)
                .toList()
        );
    }
}
