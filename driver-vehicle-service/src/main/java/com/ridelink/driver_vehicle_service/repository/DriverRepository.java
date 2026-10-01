package com.ridelink.driver_vehicle_service.repository;

import com.ridelink.driver_vehicle_service.model.Driver;
import com.ridelink.driver_vehicle_service.model.DriverStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {

    Optional<Driver> findByAccountId(String accountId);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    List<Driver> findByAvailabilityStatus(DriverStatus availabilityStatus);

    List<Driver> findByAvailabilityStatusAndServiceArea(
            DriverStatus availabilityStatus,
            String serviceArea
    );
}
