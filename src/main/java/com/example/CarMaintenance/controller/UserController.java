package com.example.CarMaintenance.controller;

import com.example.CarMaintenance.request.LoginUserRequest;
import com.example.CarMaintenance.request.RegisterUserRequest;
import com.example.CarMaintenance.response.LoginResponse;
import com.example.CarMaintenance.response.RegisterUserResponse;
import com.example.CarMaintenance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        try {
            service.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(true, "User created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new RegisterUserResponse(false, e.getMessage()));
        }
    }


    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserRequest request) {
        return ResponseEntity.ok(service.login(request));
    }







}
