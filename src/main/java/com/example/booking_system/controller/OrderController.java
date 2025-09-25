package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateOrderRequestDto;
import com.example.booking_system.dto.OrderResponseDto;
import com.example.booking_system.service.OrderService;
import com.example.booking_system.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationUtils.validate(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(requestDto));
    }

    @GetMapping("/users/{userId}/order")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable UUID userId) {
        return ResponseEntity.ok(orderService.getOrderForUser(userId));
    }
}
