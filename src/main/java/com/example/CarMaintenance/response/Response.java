package com.example.CarMaintenance.response;

import com.example.CarMaintenance.model.Car;
import com.example.CarMaintenance.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private boolean success;
    private String message;
    private Driver driver;
    private Car car;

}
