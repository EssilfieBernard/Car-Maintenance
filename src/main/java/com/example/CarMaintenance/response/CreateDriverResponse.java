package com.example.CarMaintenance.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateDriverResponse {
    private boolean success;
    private String message;
}
