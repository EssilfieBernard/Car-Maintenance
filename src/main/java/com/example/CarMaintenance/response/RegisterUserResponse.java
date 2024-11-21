package com.example.CarMaintenance.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserResponse {
    private boolean success;
    private String message;

}
