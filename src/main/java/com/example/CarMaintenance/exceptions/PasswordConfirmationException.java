package com.example.CarMaintenance.exceptions;

public class PasswordConfirmationException extends RuntimeException {
    public PasswordConfirmationException() {
        super("Password confirmation does not match.");
    }
}
