package com.example.demo.Exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("this user is not found");
    }
}
