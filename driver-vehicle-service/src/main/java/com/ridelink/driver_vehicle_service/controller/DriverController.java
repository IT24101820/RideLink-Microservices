package com.ridelink.driver_vehicle_service.controller;

import com.ridelink.driver_vehicle_service.dto.*;
import com.ridelink.driver_vehicle_service.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@Valid @RequestBody CreateDriverRequest request) {
        DriverResponse response = driverService.createDriver(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponse> getDriverById(@PathVariable String driverId) {
        DriverResponse response = driverService.getDriverById(driverId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable String driverId,
            @Valid @RequestBody UpdateDriverRequest request) {
        DriverResponse response = driverService.updateDriver(driverId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{driverId}/availability")
    public ResponseEntity<DriverResponse> updateAvailability(
            @PathVariable String driverId,
            @Valid @RequestBody AvailabilityUpdateRequest request) {
        DriverResponse response = driverService.updateAvailability(driverId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{driverId}/location")
    public ResponseEntity<DriverResponse> updateLocation(
            @PathVariable String driverId,
            @Valid @RequestBody LocationUpdateRequest request) {
        DriverResponse response = driverService.updateLocation(driverId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{driverId}/service-area")
    public ResponseEntity<DriverResponse> updateServiceArea(
            @PathVariable String driverId,
            @Valid @RequestBody ServiceAreaUpdateRequest request) {
        DriverResponse response = driverService.updateServiceArea(driverId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DriverResponse>> getAvailableDrivers(
            @RequestParam(required = false) String serviceArea) {
        List<DriverResponse> responses;
        if (serviceArea != null && !serviceArea.isBlank()) {
            responses = driverService.getAvailableDriversByServiceArea(serviceArea);
        } else {
            responses = driverService.getAvailableDrivers();
        }
        return ResponseEntity.ok(responses);
    }
}
