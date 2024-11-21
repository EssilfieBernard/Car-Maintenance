package com.example.CarMaintenance.exceptions;

public class CarInUseException extends RuntimeException {
    public CarInUseException(String message) {
        super(message);
    }
}
