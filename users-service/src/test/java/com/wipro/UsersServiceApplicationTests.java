package com.wipro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.model.User;
import com.wipro.repository.UserRepository;
import com.wipro.service.UserService;

@ExtendWith(MockitoExtension.class)
class UsersServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLocation("Chennai");
        user.setMobileNumber("9876543210");
    }

    @Test
    void testRegisterUser() {
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticateSuccess() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        User authenticatedUser = userService.authenticate(user.getEmail(), "password");

        assertNotNull(authenticatedUser);
        assertEquals(user.getEmail(), authenticatedUser.getEmail());
    }

    @Test
    void testAuthenticateFailure() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticate(user.getEmail(), "wrongpassword");
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }
}