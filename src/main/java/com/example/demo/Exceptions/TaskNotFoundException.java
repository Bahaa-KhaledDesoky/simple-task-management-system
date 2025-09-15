package com.example.demo.Exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("cant find this task");
    }
}
