package com.sweetshop.SweetShop.config;

import com.sweetshop.SweetShop.entity.Role;
import com.sweetshop.SweetShop.entity.User;
import com.sweetshop.SweetShop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if admin already exists to avoid duplicates
        if (!userRepository.existsByEmail("admin@sweetshop.com")) {

            User admin = User.builder()
                    .username("admin")
                    .email("admin@sweetshop.com")
                    .password(passwordEncoder.encode("admin@123")) // Securely hash the password
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("✅ ADMIN USER CREATED: admin@sweetshop.com / admin123");
        }
    }
}