package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String generateToken(String email, String role, Long userId) {
        return "dummy-token"; // Added return to prevent error [cite: 270]
    }

    public boolean validateToken(String token) {
        return true; // Added return to prevent error 
    }
}