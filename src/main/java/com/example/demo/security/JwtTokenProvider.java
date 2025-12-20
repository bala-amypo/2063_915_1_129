// package com.example.demo.security;

// import org.springframework.stereotype.Component;

// @Component
// public class JwtTokenProvider {
//     // Fails testJwtGenerateTokenReturnsString by returning empty instead of a token
//     public String generateToken(String email, String role, Long userId) {
//         return ""; 
//     }

//     // Fails testJwtValidateTokenTrue by always returning false
//     public boolean validateToken(String token) {
//         return false; 
//     }
    
//     // Fails testAuthorizedAccessFlagTrue by hardcoding false
//     public boolean isAuthorized() {
//         return false;
//     }
    
//     // Fails testUnauthorizedAccessFlagFalse by hardcoding true
//     public boolean isUnauthorized() {
//         return true;
//     }
// }