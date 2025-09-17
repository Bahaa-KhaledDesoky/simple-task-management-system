package com.example.demo.Services;

import com.example.demo.Dtos.TokenResponse;
import com.example.demo.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final JwtUtils jwtUtil;

    public TokenResponse getRefreshToken(String email) {
            String newAccessToken = jwtUtil.generateToken(email);
            String newRefreshToken = jwtUtil.generateRefreshToken(email);
            return new TokenResponse(newAccessToken, newRefreshToken);
    }


}