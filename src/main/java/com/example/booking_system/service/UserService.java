package com.example.booking_system.service;

import com.example.booking_system.dto.UserDTO;
import com.example.booking_system.model.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    UUID createUser(UserDTO newUser);
}
