package com.example.demo.controllers;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.Registration;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.Dtos.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImp userServiceImp;
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@Validated @RequestBody Registration registration) {
        Integer id= userServiceImp.signUp(registration);
        return ResponseEntity.ok(id);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LogInRequest logInRequest) {
        TokenResponse token=userServiceImp.login(logInRequest);
        return ResponseEntity.ok(token);
    }
    @GetMapping("/{refresh}")
    public ResponseEntity<?> accessToken(@PathVariable String refresh) {
        String token=userServiceImp.accessToken(refresh);
        return ResponseEntity.ok(token);
    }
}







