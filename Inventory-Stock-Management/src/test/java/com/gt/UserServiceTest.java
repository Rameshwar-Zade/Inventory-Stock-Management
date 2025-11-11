package com.gt;

import com.gt.entity.User;
import com.gt.repository.UserRepository;
import com.gt.service.UserService;  // ✅ Add this import
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService; // ✅ This will now work because of the import

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByEmail_Found() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("Rameshwar");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("test@gmail.com");

        assertNotNull(result);
        assertEquals("Rameshwar", result.getName());
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
    }

    @Test
    void testFindUserByEmail_NotFound() {
        when(userRepository.findByEmail("unknown@gmail.com")).thenReturn(Optional.empty());

        User result = userService.findByEmail("unknown@gmail.com"); // ✅ Works fine now

        assertNull(result);
        verify(userRepository, times(1)).findByEmail("unknown@gmail.com");
    }
}
