package com.example.CarMaintenance.controller;

import com.example.CarMaintenance.exceptions.CarInUseException;
import com.example.CarMaintenance.model.Car;
import com.example.CarMaintenance.model.Driver;
import com.example.CarMaintenance.repository.CarRepository;
import com.example.CarMaintenance.repository.DriverRepository;
import com.example.CarMaintenance.repository.UserRepository;
import com.example.CarMaintenance.response.CreateDriverResponse;
import com.example.CarMaintenance.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("drivers")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("create-driver")
    public ResponseEntity<CreateDriverResponse> createDriver() {
        // Get the username from the security context (authenticated user)
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Retrieve the user by username from the user repository
        var optionalUser = userRepository.findByUsername(username);

        // If the user is not found, return a 404 response
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CreateDriverResponse(false, "User not found"));
        }

        // Get the existing user
        var user = optionalUser.get();

        // Check if the user already has a driver (optional check to prevent multiple drivers per user)
        if (user instanceof Driver) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateDriverResponse(false, "User already has a driver"));
        }

        Driver driver = new Driver();
        driver.setUser(user); // Set the existing user
        driver.setPassword(user.getPassword()); // Set the password (ensure it's properly hashed if needed)

        driverRepository.save(driver);
        return ResponseEntity.ok(new CreateDriverResponse(true, "Driver created successfully."));
    }


    @PostMapping("{driverId}/select-car/{carId}")
    public ResponseEntity<Response> selectCar(@PathVariable int driverId, @PathVariable int carId) {
        var optionalDriver = driverRepository.findById(driverId);
        var optionalCar = carRepository.findById(carId);

        if (optionalDriver.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(false, "Driver not found", new Driver(), new Car()));

        if (optionalCar.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(false, "Car not found.", new Driver(), new Car()));

        var car = optionalCar.get();
        var driver = optionalDriver.get();
        if (car.getDriver() != null)
            throw new CarInUseException("Car already in use.");

        car.setDriver(driver);
        driver.setSelectedCar(car);
        carRepository.save(car);
        driverRepository.save(driver);
        return ResponseEntity.ok(new Response(true, "Car selected successfully", driver, car));

    }



    @PostMapping("{driverId}/deselect-car")
    public ResponseEntity<Response> deselectCar(@PathVariable int driverId) {
        var optionalDriver = driverRepository.findById(driverId);

        if (optionalDriver.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(false, "Driver not found.", new Driver(), new Car()));


        var driver = optionalDriver.get();
        var car = driver.getSelectedCar();

        driver.setSelectedCar(null);
        car.setDriver(null);
        carRepository.save(car);
        driverRepository.save(driver);

        return ResponseEntity.ok(new Response(true, "Car deselected successfully", driver, car));
    }



    @GetMapping
    public ResponseEntity<List<Driver>> getAllCars() {
        return ResponseEntity.ok(driverRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable int id) {
        return driverRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Driver()));

    }


}
