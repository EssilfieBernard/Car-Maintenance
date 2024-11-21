package com.example.CarMaintenance.repository;

import com.example.CarMaintenance.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
