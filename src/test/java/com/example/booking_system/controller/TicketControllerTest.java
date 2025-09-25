package com.example.booking_system.controller;

import com.example.booking_system.model.Event;
import com.example.booking_system.model.Ticket;
import com.example.booking_system.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    @Test
    void getTicketsByEventId_returnsListOfTickets() throws Exception {
        UUID eventId = UUID.randomUUID();

        // prepare a Ticket entity
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setSector("A");
        ticket.setRowNumber(1);
        ticket.setSeatNumber(5);
        ticket.setPrice(BigDecimal.valueOf(50));
        ticket.setDateTime(LocalDateTime.now());

        Event event = new Event();
        event.setId(eventId);
        ticket.setEvent(event);

        Mockito.when(ticketService.getTicketByEventId(any()))
                .thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/tickets/{eventId}", eventId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].ticketId").value(ticket.getId().toString()))
                .andExpect(jsonPath("$[0].sector").value("A"))
                .andExpect(jsonPath("$[0].rowNumber").value(1))
                .andExpect(jsonPath("$[0].seatNumber").value(5))
                .andExpect(jsonPath("$[0].price").value(50))
                .andExpect(jsonPath("$[0].eventId").value(eventId.toString()));

    }

}
