package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.dto.*;
import com.ridelink.driver_vehicle_service.exception.DriverNotFoundException;
import com.ridelink.driver_vehicle_service.exception.DuplicateLicenseException;
import com.ridelink.driver_vehicle_service.model.Driver;
import com.ridelink.driver_vehicle_service.model.DriverStatus;
import com.ridelink.driver_vehicle_service.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverResponse createDriver(CreateDriverRequest request) {

        if (driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateLicenseException(request.getLicenseNumber());
        }

        Driver driver = Driver.builder()
                .accountId(request.getAccountId())
                .licenseNumber(request.getLicenseNumber())
                .serviceArea(request.getServiceArea())
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(driverRepository.save(driver));
    }

    @Override
    public DriverResponse getDriverById(String id) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        return mapToResponse(driver);
    }

    @Override
    public DriverResponse updateDriver(String id, UpdateDriverRequest request) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        if (request.getLicenseNumber() != null) {
            driver.setLicenseNumber(request.getLicenseNumber());
        }

        if (request.getServiceArea() != null) {
            driver.setServiceArea(request.getServiceArea());
        }

        driver.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(driverRepository.save(driver));
    }

    @Override
    public DriverResponse updateAvailability(String id, AvailabilityUpdateRequest request) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        driver.setAvailabilityStatus(request.getAvailabilityStatus());
        driver.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(driverRepository.save(driver));
    }

    @Override
    public DriverResponse updateLocation(String id, LocationUpdateRequest request) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        driver.setLatitude(request.getLatitude());
        driver.setLongitude(request.getLongitude());
        driver.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(driverRepository.save(driver));
    }

    @Override
    public DriverResponse updateServiceArea(String id, ServiceAreaUpdateRequest request) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        driver.setServiceArea(request.getServiceArea());
        driver.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(driverRepository.save(driver));
    }

    @Override
    public List<DriverResponse> getAvailableDrivers() {

        return driverRepository
                .findByAvailabilityStatus(DriverStatus.AVAILABLE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DriverResponse> getAvailableDriversByServiceArea(String serviceArea) {

        return driverRepository
                .findByAvailabilityStatusAndServiceArea(DriverStatus.AVAILABLE, serviceArea)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DriverResponse mapToResponse(Driver driver) {

        return DriverResponse.builder()
                .id(driver.getId())
                .accountId(driver.getAccountId())
                .licenseNumber(driver.getLicenseNumber())
                .serviceArea(driver.getServiceArea())
                .availabilityStatus(driver.getAvailabilityStatus())
                .latitude(driver.getLatitude())
                .longitude(driver.getLongitude())
                .createdAt(driver.getCreatedAt())
                .updatedAt(driver.getUpdatedAt())
                .build();
    }
}
