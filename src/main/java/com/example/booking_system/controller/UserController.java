package com.example.booking_system.controller;

import com.example.booking_system.dto.UserDTO;
import com.example.booking_system.service.UserService;
import com.example.booking_system.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UUID> createUser(@Valid @RequestBody UserDTO newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationUtils.validate(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUser));
    }
}
