package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Required for POST requests in testing [cite: 169]
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allows all APIs and Swagger to work for the review [cite: 278]
            );
        return http.build();
    }
}