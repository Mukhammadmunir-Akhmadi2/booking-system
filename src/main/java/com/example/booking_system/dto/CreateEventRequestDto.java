package com.example.booking_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateEventRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String dateTime;
    @NotBlank
    private String venue;
    @Min(1)
    private int totalTickets;
    @NotEmpty
    private List<CreateTicketDto> tickets;
}
