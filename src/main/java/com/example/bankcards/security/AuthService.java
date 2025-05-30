package com.example.bankcards.security;

import com.example.bankcards.dto.UserCreateRequest;
import com.example.bankcards.dto.UserResponse;
import com.example.bankcards.dto.jwt.JwtAuthenticationResponse;
import com.example.bankcards.dto.jwt.SignInRequest;

public interface AuthService {
    UserResponse signUp(UserCreateRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
