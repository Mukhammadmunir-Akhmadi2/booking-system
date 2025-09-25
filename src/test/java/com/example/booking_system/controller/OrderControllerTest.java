package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateOrderRequestDto;
import com.example.booking_system.dto.OrderResponseDto;
import com.example.booking_system.dto.TicketDto;
import com.example.booking_system.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_shouldReturnCreatedOrder() throws Exception {
        // given
        UUID userId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();

        CreateOrderRequestDto request = new CreateOrderRequestDto();
        request.setUserId(userId);
        request.setTotalAmount(BigDecimal.valueOf(100.50));

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketId(ticketId);

        request.setTickets(List.of(ticketDto));

        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(UUID.randomUUID());
        response.setTotalAmount(BigDecimal.valueOf(100.50));

        when(orderService.createOrder(any(CreateOrderRequestDto.class)))
                .thenReturn(response);

        // when/then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalAmount").value(100.50));
    }

    @Test
    void getOrder_shouldReturnOrderForUser() throws Exception {
        UUID userId = UUID.randomUUID();

        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(UUID.randomUUID());
        response.setTotalAmount(BigDecimal.TEN);

        when(orderService.getOrderForUser(userId)).thenReturn(response);

        mockMvc.perform(get("/api/users/{userId}/order", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalAmount").value(10));
    }
}
