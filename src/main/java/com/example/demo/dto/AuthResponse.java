package com.example.demo.dto;

import java.time.LocalDateTime;

public class AuthResponse {

    private String token;
    private LocalDateTime issuedAt;

    public AuthResponse(String token) {
        this.token = token;
        this.issuedAt = LocalDateTime.now();
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    /* ================= BUSINESS LOGIC ================= */

    public boolean isTokenExpired() {
        return issuedAt.plusHours(2).isBefore(LocalDateTime.now());
    }
}
