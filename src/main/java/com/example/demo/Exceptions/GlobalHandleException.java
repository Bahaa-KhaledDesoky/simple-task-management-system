package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorDetails.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<?> handleUserExistException(UserExistException ex){
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("HttpStatus",""+HttpStatus.BAD_REQUEST.value());
        errorDetails.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("HttpStatus",""+HttpStatus.BAD_REQUEST.value());
        errorDetails.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WrongPasswordExceprion.class)
    public ResponseEntity<?> handleWrongPasswordExceprion(WrongPasswordExceprion ex){
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("HttpStatus",""+HttpStatus.BAD_REQUEST.value());
        errorDetails.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException ex){
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("HttpStatus",""+HttpStatus.BAD_REQUEST.value());
        errorDetails.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskForbiddenException.class)
    public ResponseEntity<?> handleTaskForbiddenException(TaskForbiddenException ex){
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("HttpStatus",""+HttpStatus.FORBIDDEN.value());
        errorDetails.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex){
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("HttpStatus",""+HttpStatus.BAD_REQUEST.value());
        errorDetails.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
