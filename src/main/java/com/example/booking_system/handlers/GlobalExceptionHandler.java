package com.example.booking_system.handlers;

import com.example.booking_system.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("Illegal argument: {}", e.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "details", e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        logger.warn("Illegal state: {}", e.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", "Resource not found", "details", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception e) {
        logger.error("Unexpected error occurred: ", e);
        return ResponseEntity.status(500).body(Map.of("Internal Server Error", "An unexpected error occurred"));
    }
}
