package com.example.booking_system.repository;

import com.example.booking_system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User sampleUser;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();

        sampleUser = new User();
        sampleUser.setId(userId);
        sampleUser.setFullName("Alice Smith");
        sampleUser.setPhoneNumber("+998901000001");
    }

    @Test
    void saveUser_success() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User saved = userRepository.save(sampleUser);

        assertNotNull(saved);
        assertEquals("Alice Smith", saved.getFullName());
        verify(userRepository).save(sampleUser);
    }

    @Test
    void findById_returnsUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userRepository.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void findAll_returnsList() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        List<User> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals("Alice Smith", users.get(0).getFullName());
        verify(userRepository).findAll();
    }

    @Test
    void deleteById_success() {
        doNothing().when(userRepository).deleteById(userId);

        userRepository.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }
}
