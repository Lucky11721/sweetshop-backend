package com.sweetshop.SweetShop;

import com.sweetshop.SweetShop.dto.AuthResponse;
import com.sweetshop.SweetShop.dto.LoginRequest;
import com.sweetshop.SweetShop.dto.RegisterRequest;
import com.sweetshop.SweetShop.entity.Role;
import com.sweetshop.SweetShop.entity.User;
import com.sweetshop.SweetShop.exception.BadRequestException;
import com.sweetshop.SweetShop.repository.UserRepository;
import com.sweetshop.SweetShop.security.jwt.JwtService;
import com.sweetshop.SweetShop.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("testuser", "test@example.com", "password123");
        loginRequest = new LoginRequest("test@example.com", "password123");
        mockUser = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    // --- Register Tests ---

    @Test
    public void register_ShouldSaveUserAndReturnToken_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(anyString())).thenReturn("mock-jwt-token");

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        assertNotNull(response.getToken());
        assertEquals("User registered successfully", response.getMessage());
    }

    @Test
    public void register_ShouldThrowException_WhenEmailExists() {
        // Arrange
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            authService.register(registerRequest);
        });
        assertEquals("Email already exists", exception.getMessage());
    }

    // --- Login Tests ---

    @Test
    public void login_ShouldReturnToken_WhenCredentialsAreCorrect() {
        // Arrange
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(jwtService.generateToken(mockUser.getEmail())).thenReturn("mock-jwt-token");

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    public void login_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    public void login_ShouldThrowException_WhenPasswordIsWrong() {
        // Arrange
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }
}