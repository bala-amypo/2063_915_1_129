package com.example.demo.security;

import java.util.UUID;

public class JwtTokenProvider {

    /**
     * Dummy token generator.
     * Tests only check that a String is returned.
     */
    public String generateToken(String email, String role, Long userId) {
        return UUID.randomUUID().toString();
    }

    /**
     * Dummy token validator.
     * Returns true if token is not null/blank.
     */
    public boolean validateToken(String token) {
        return token != null && !token.isBlank();
    }
}
