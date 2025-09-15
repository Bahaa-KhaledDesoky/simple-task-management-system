package com.example.demo.controller;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.SignIn;
import com.example.demo.Dtos.TokenResponse;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.controllers.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserServiceImp userServiceImp;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // given
        SignIn signIn = new SignIn("test@gmail.com","test","000000");
        when(userServiceImp.signUp(signIn)).thenReturn(1);

        // when
        ResponseEntity<?> response = authController.signUp(signIn);

        // then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody());
        verify(userServiceImp, times(1)).signUp(signIn);
    }

    @Test
    void testLogin() {
        // given
        LogInRequest logInRequest = new LogInRequest("test@gmail.com","000000");
        TokenResponse mockToken = new TokenResponse("accessToken123", "refreshToken123");
        when(userServiceImp.login(logInRequest)).thenReturn(mockToken);

        // when
        ResponseEntity<?> response = authController.login(logInRequest);

        // then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockToken, response.getBody());
        verify(userServiceImp, times(1)).login(logInRequest);
    }

    @Test
    void testAccessToken() {
        // given
        String refresh = "refreshToken123";
        when(authService.accessToken(refresh)).thenReturn("newAccessToken456");

        // when
        ResponseEntity<?> response = authController.accessToken(refresh);

        // then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("newAccessToken456", response.getBody());
        verify(authService, times(1)).accessToken(refresh);
    }
}
