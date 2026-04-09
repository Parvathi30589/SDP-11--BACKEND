package com.studentproject.service;

import com.studentproject.dto.auth.AuthResponse;
import com.studentproject.dto.auth.LoginRequest;
import com.studentproject.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
