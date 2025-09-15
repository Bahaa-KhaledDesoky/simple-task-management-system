package com.example.demo.Exceptions;

public class TaskForbiddenException extends RuntimeException{
    public TaskForbiddenException() {
        super("you cant access this task");
    }
}
