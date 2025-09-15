package com.example.demo.Services;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.SignIn;
import com.example.demo.Dtos.TokenResponse;
import com.example.demo.Exceptions.UserExistException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Exceptions.WrongPasswordExceprion;
import com.example.demo.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserService {
    AppUser getUser(HttpServletRequest request);
    Integer signUp(SignIn signIn);
    boolean userExist(String email);

    AppUser findUserById(Integer id);
    AppUser findUserByEmail(String email);
    TokenResponse login(LogInRequest logInRequest);
    boolean logOut(Integer id);
}
