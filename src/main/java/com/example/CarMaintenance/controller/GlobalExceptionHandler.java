package com.example.CarMaintenance.controller;

import com.example.CarMaintenance.exceptions.CarInUseException;
import com.example.CarMaintenance.exceptions.PasswordConfirmationException;
import com.example.CarMaintenance.exceptions.UsernameAlreadyExistsException;
import com.example.CarMaintenance.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarInUseException.class)
    public ResponseEntity<Response> handlecarInUseException(CarInUseException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(false, exception.getMessage(), null, null));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ResponseEntity<String> handlePasswordConfirmationException(PasswordConfirmationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
