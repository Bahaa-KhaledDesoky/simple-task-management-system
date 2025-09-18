package com.example.demo.controllers;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.Registration;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.Dtos.TokenResponse;
import com.example.demo.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request)
    {
        AppUser user =userServiceImp.getUser(request);
        boolean flag = userServiceImp.logOut(user);
        return ResponseEntity.ok(flag);
    }
}







