package com.example.CarMaintenance.response;

import com.example.CarMaintenance.model.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private boolean success;
    private String message;
    private Car car;
}
