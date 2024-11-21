package com.example.CarMaintenance.filter;

import com.example.CarMaintenance.model.Driver;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class DriverSpecification {
    private String licensePlate;
    private String engineType;

    public DriverSpecification(String licensePlate, String engineType) {
        this.licensePlate = licensePlate;
        this.engineType = engineType;
    }

    public Specification<Driver> buildSpecification() {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (licensePlate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("car").get("licensePlate"), licensePlate));
            }
            if (engineType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("car").get("engineType"), engineType));
            }
            return predicate;
        };
    }
}

