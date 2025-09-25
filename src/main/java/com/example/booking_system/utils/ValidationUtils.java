package com.example.booking_system.utils;

import org.springframework.validation.BindingResult;

import java.util.List;
public class ValidationUtils {
    public static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .toList();

            throw new IllegalArgumentException(errors.toString());
        }
    }
}
