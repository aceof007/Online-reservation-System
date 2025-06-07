package com.ORS.Online_reservation_System.config;

import com.ORS.Online_reservation_System.model.User;
import com.ORS.Online_reservation_System.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {
            String adminEmail = "admin@example.com";

            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = User.builder()
                        .username("Admin")
                        .password(new BCryptPasswordEncoder().encode("admin123")) // secure password
                        .email("admin@example.com")
                        .firstName("System")
                        .lastName("Administrator")
                        .phoneNumber("1234567890")
                        .registrationDate(new Date())
                        .status("ACTIVE")
                        .role("ADMIN")
                        .build();

                userRepository.save(admin);
                System.out.println("Admin user created.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
