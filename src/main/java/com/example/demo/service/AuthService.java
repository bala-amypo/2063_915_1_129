package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;

public interface AuthService {
    // Handles user registration logic
    User register(User user);
    
    // Handles user login and returns a JWT token [cite: 124]
    AuthResponse login(AuthRequest authRequest);
}