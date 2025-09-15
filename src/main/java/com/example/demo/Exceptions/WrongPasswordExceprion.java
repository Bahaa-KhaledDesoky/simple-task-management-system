package com.example.demo.Exceptions;

public class WrongPasswordExceprion extends RuntimeException{
    public WrongPasswordExceprion() {
        super("wrong password");
    }
}
