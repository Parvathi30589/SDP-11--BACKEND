package com.studentproject.config;

import com.studentproject.entity.User;
import com.studentproject.entity.enums.Role;
import com.studentproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@school.com")) {
            User admin = User.builder()
                    .name("Admin Teacher")
                    .email("admin@school.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.TEACHER)
                    .build();
            userRepository.save(admin);
        }
    }
}
