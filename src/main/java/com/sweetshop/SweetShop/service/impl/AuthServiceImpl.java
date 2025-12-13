package com.sweetshop.SweetShop.service.impl;

import com.sweetshop.SweetShop.dto.AuthResponse;
import com.sweetshop.SweetShop.dto.LoginRequest;
import com.sweetshop.SweetShop.dto.RegisterRequest;
import com.sweetshop.SweetShop.entity.Role;
import com.sweetshop.SweetShop.entity.User;
import com.sweetshop.SweetShop.repository.UserRepository;
import com.sweetshop.SweetShop.security.jwt.JwtService; //
import com.sweetshop.SweetShop.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // 1. Add JwtService field

    // 2. Inject JwtService in the constructor
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // 3. Generate real JWT token
        String jwtToken = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .message("User registered successfully")
                .token(jwtToken) // 4. Return the real token instead of "DUMMY_TOKEN"
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // 5. Generate real JWT token
        String jwtToken = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .message("Login successful")
                .token(jwtToken) // 6. Return the real token
                .role(user.getRole().name())
                .build();
    }
}