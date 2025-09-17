package com.example.demo.controllers;

import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemController {
    private final UserServiceImp userServiceImp ;
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request)
    {
        AppUser user =userServiceImp.getUser(request);
        boolean flag = userServiceImp.logOut(user);
        return ResponseEntity.ok(flag);
    }

}
