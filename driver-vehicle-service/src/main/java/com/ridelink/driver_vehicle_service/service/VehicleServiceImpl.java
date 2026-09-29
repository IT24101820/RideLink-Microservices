package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.dto.*;
import com.ridelink.driver_vehicle_service.model.Vehicle;
import com.ridelink.driver_vehicle_service.model.VehicleStatus;
import com.ridelink.driver_vehicle_service.repository.DriverRepository;
import com.ridelink.driver_vehicle_service.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public VehicleResponse createVehicle(CreateVehicleRequest request) {
        if (!driverRepository.existsById(request.getDriverId())) {
            throw new RuntimeException("Driver not found with id: " + request.getDriverId());
        }

        if (vehicleRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new RuntimeException("Vehicle with registration number " + request.getRegistrationNumber() + " already exists");
        }

        LocalDateTime now = LocalDateTime.now();
        Vehicle vehicle = Vehicle.builder()
                .driverId(request.getDriverId())
                .registrationNumber(request.getRegistrationNumber())
                .brand(request.getBrand())
                .model(request.getModel())
                .vehicleType(request.getVehicleType())
                .color(request.getColor())
                .year(request.getYear())
                .status(VehicleStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(savedVehicle);
    }

    @Override
    public VehicleResponse getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return mapToResponse(vehicle);
    }

    @Override
    public List<VehicleResponse> getVehiclesByDriverId(String driverId) {
        if (!driverRepository.existsById(driverId)) {
            throw new RuntimeException("Driver not found with id: " + driverId);
        }

        return vehicleRepository.findByDriverId(driverId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse updateVehicle(String id, UpdateVehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        if (request.getRegistrationNumber() != null && !request.getRegistrationNumber().isBlank()) {
            if (!request.getRegistrationNumber().equals(vehicle.getRegistrationNumber()) &&
                    vehicleRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
                throw new RuntimeException("Vehicle with registration number " + request.getRegistrationNumber() + " already exists");
            }
            vehicle.setRegistrationNumber(request.getRegistrationNumber());
        }

        if (request.getBrand() != null && !request.getBrand().isBlank()) {
            vehicle.setBrand(request.getBrand());
        }

        if (request.getModel() != null && !request.getModel().isBlank()) {
            vehicle.setModel(request.getModel());
        }

        if (request.getVehicleType() != null) {
            vehicle.setVehicleType(request.getVehicleType());
        }

        if (request.getColor() != null && !request.getColor().isBlank()) {
            vehicle.setColor(request.getColor());
        }

        if (request.getYear() != null) {
            vehicle.setYear(request.getYear());
        }

        vehicle.setUpdatedAt(LocalDateTime.now());
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(updatedVehicle);
    }

    @Override
    public VehicleResponse updateVehicleStatus(String id, VehicleStatusUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        vehicle.setStatus(request.getStatus());
        vehicle.setUpdatedAt(LocalDateTime.now());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(updatedVehicle);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .driverId(vehicle.getDriverId())
                .registrationNumber(vehicle.getRegistrationNumber())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .vehicleType(vehicle.getVehicleType())
                .color(vehicle.getColor())
                .year(vehicle.getYear())
                .status(vehicle.getStatus())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}
