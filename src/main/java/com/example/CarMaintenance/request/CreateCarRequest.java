package com.example.CarMaintenance.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCarRequest {
    private String licensePlate;
    private int seatCount;
    private boolean convertible;
    private double rating;
    private String engineType;
    private String manufacturer;

}
