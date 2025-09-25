package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateEventRequest;
import com.example.booking_system.dto.EventDto;
import com.example.booking_system.mapper.EventMapper;
import com.example.booking_system.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {
    private EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> listEvents() {
        return ResponseEntity.ok(eventService.getAllEvent()
                .stream()
                .map(EventMapper::toDto)
                .toList()
        );
    }

    @PostMapping("/admin/event")
    public ResponseEntity<String> createEvent(@Valid @RequestBody CreateEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request));
    }
}
