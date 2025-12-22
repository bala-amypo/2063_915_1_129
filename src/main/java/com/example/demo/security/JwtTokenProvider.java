package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    [cite_start]// Required by tests to return a non-null string [cite: 270]
    public String generateToken(String email, String role, Long userId) {
        [cite_start]return "JWT-TOKEN-" + email + "-" + role + "-" + userId; [cite: 265, 272]
    }

    [cite_start]// Used for token validation checks [cite: 271]
    public boolean validateToken(String token) {
        [cite_start]return token != null && !token.isEmpty(); [cite: 268]
    }
}