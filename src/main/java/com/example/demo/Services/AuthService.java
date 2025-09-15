package com.example.demo.Services;

import com.example.demo.Dtos.TokenResponse;
import com.example.demo.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final JwtUtils jwtUtil;
    public String accessToken(String refreshToken) {

        if (jwtUtil.validateToken(refreshToken)) {
            String email = jwtUtil.extractEmail(refreshToken);
            String newAccessToken = jwtUtil.generateToken(email);
            return newAccessToken;
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
    public TokenResponse getRefreshToken(String email) {
            String newAccessToken = jwtUtil.generateToken(email);
            String newRefreshToken = jwtUtil.generateRefreshToken(email);
            return new TokenResponse(newAccessToken, newRefreshToken);
    }


}