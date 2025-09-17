package com.example.demo.Exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Invalid refresh token");
    }
}
