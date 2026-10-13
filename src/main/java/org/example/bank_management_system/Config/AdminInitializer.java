package org.example.bank_management_system.Config;

import org.example.bank_management_system.Model.Role;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("Admin123"));
                admin.setName("System Admin");
                admin.setEmail("admin@example.com");
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Initial admin account created successfully");
            }
        };
    }
}