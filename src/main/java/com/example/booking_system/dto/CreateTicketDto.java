package com.example.booking_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTicketDto {
    @NotBlank
    private String sector;
    @NotNull
    private Integer rowNumber;
    @NotNull
    private Integer seatNumber;
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    public BigDecimal price;
}
