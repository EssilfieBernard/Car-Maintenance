package com.example.CarMaintenance.controller;

import com.example.CarMaintenance.model.Car;
import com.example.CarMaintenance.repository.CarRepository;
import com.example.CarMaintenance.response.CarResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;


    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(new CarResponse(true, "Car created successfully", car));
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        var optionalCar = carRepository.findById(id);

        if (optionalCar.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var car = optionalCar.get();
        return ResponseEntity.ok(car);

    }

    @PutMapping("{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable int id, @RequestBody Car car) {
        var optionalCar = carRepository.findById(id);

        if (optionalCar.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CarResponse(true, "Car not found.", new Car()));

        var foundCar = optionalCar.get();
        foundCar.setLicensePlate(car.getLicensePlate());
        foundCar.setDriver(car.getDriver());
        foundCar.setConvertible(car.isConvertible());
        foundCar.setRating(car.getRating());
        foundCar.setManufacturer(car.getManufacturer());
        foundCar.setSeatCount(car.getSeatCount());
        foundCar.setEngineType(car.getEngineType());
        return ResponseEntity.ok(new CarResponse(true, "Car details updated successfully.", foundCar));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<CarResponse> deleteCar(@PathVariable int id) {
        var optionalCar = carRepository.findById(id);

        if (optionalCar.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CarResponse(false, "Car not found", new Car()));

        var car = optionalCar.get();
        carRepository.delete(car);
        return ResponseEntity.status(HttpStatus.OK).body(new CarResponse(true, "Car deleted successfully", car));
    }

}
