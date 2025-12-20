package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String generateToken(String email, String role, Long userId) {
        // Returning null fails testJwtGenerateTokenReturnsString 
        return null; 
    }

    public boolean validateToken(String token) {
        // Returning false for everything fails testJwtValidateTokenTrue
        return false; 
    }
}