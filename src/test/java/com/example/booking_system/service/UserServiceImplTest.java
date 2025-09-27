package com.example.booking_system.service;

import com.example.booking_system.dto.UserDTO;
import com.example.booking_system.exceptions.ResourceNotFoundException;
import com.example.booking_system.model.User;
import com.example.booking_system.repository.UserRepository;
import com.example.booking_system.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        UUID id = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setFullName("Alice");
        mockUser.setPhoneNumber("12345");

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals("Alice", result.getFullName());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getUserById_shouldThrow_whenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(id));

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void createUser_shouldSaveAndReturnId() {
        UserDTO dto = new UserDTO();
        dto.setFullName("Bob");
        dto.setPhoneNumber("67890");

        User saved = new User();
        UUID generatedId = UUID.randomUUID();
        saved.setId(generatedId);
        saved.setFullName("Bob");
        saved.setPhoneNumber("67890");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        UUID resultId = userService.createUser(dto);

        assertEquals(generatedId, resultId);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
