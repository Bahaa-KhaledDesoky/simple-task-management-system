package com.example.demo.controllers;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.SignIn;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.Dtos.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImp userServiceImp;
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignIn signIn) {
        Integer id= userServiceImp.signUp(signIn);
        return ResponseEntity.ok(id);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest logInRequest) {
        TokenResponse token=userServiceImp.login(logInRequest);
        return ResponseEntity.ok(token);
    }
    @GetMapping("/{refresh}")
    public ResponseEntity<?> accessToken(@PathVariable String refresh) {
        String token=authService.accessToken(refresh);
        return ResponseEntity.ok(token);
    }
}







