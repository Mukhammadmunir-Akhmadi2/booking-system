package com.example.booking_system.service;

import com.example.booking_system.dto.UserDTO;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.User;
import com.example.booking_system.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public UUID createUser(@Valid UserDTO newUser) {
        User user = new User();
        user.setFullName(newUser.getFullName());
        user.setPhoneNumber(newUser.getPhoneNumber());

        return userRepository.save(user).getId();
    }
}
