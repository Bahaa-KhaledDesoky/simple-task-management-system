package com.example.demo.Services;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.Registration;
import com.example.demo.Dtos.TokenResponse;
import com.example.demo.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
public interface UserService {
    AppUser getUser(HttpServletRequest request);
    Integer signUp(Registration registration);
    boolean userExist(String email);

    AppUser findUserById(Integer id);
    AppUser findUserByEmail(String email);
    TokenResponse login(LogInRequest logInRequest);
    boolean logOut(Integer id);
}
