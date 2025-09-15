package com.example.demo.Exceptions;

public class UserExistException extends RuntimeException{
    public UserExistException() {
        super("this email is already used");
    }
}
