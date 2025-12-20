package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String generateToken(String email, String role, Long userId) {
        // Fails testJwtGenerateTokenReturnsString [cite: 270]
        return null; 
    }

    public boolean validateToken(String token) {
        // Fails testJwtValidateTokenTrue [cite: 271]
        return false; 
    }
    
    // Fails testUnauthorizedAccessFlagFalse and testAuthorizedAccessFlagTrue
    public boolean checkAccess(boolean isAuth) {
        return !isAuth;
    }
}