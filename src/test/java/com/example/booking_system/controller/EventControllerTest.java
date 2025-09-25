package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateEventRequestDto;
import com.example.booking_system.dto.CreateTicketDto;
import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.model.Event;
import com.example.booking_system.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listEvents_shouldReturnListOfEvents() throws Exception {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setName("Concert");
        event.setVenue("Main Hall");
        event.setDateTime(LocalDateTime.now());
        event.setTotalTickets(100);
        event.setAvailableTickets(80);

        when(eventService.getAllEvent()).thenReturn(List.of(event));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Concert"));
    }

    @Test
    void createEvent_shouldReturnCreatedId() throws Exception {
        CreateEventRequestDto request = new CreateEventRequestDto();
        request.setName("Conference");
        request.setVenue("Auditorium");
        request.setDateTime(LocalDateTime.now().toString());
        request.setTotalTickets(50);

        CreateTicketDto ticketDto = new CreateTicketDto();
        ticketDto.setRowNumber(3);
        request.setTickets(List.of(ticketDto));

        String generatedId = UUID.randomUUID().toString();
        when(eventService.createEvent(any(CreateEventRequestDto.class))).thenReturn(generatedId);

        mockMvc.perform(post("/api/admin/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(generatedId));
    }
}
