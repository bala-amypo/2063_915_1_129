package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String generateToken(String email, String role, Long userId) {
        return "JWT-TOKEN-" + email + "-" + role + "-" + userId;
    }
    public boolean validateToken(String token) {
        return token != null && !token.isEmpty();
    }
}