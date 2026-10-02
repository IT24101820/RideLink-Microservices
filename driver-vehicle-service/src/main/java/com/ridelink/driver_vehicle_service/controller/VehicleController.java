package com.ridelink.driver_vehicle_service.controller;

import com.ridelink.driver_vehicle_service.dto.*;
import com.ridelink.driver_vehicle_service.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/api/vehicles")
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        VehicleResponse response = vehicleService.createVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/vehicles/{vehicleId}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable String vehicleId) {
        VehicleResponse response = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/vehicles/{vehicleId}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable String vehicleId,
            @Valid @RequestBody UpdateVehicleRequest request) {
        VehicleResponse response = vehicleService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/api/vehicles/{vehicleId}/status")
    public ResponseEntity<VehicleResponse> updateVehicleStatus(
            @PathVariable String vehicleId,
            @Valid @RequestBody VehicleStatusUpdateRequest request) {
        VehicleResponse response = vehicleService.updateVehicleStatus(vehicleId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/drivers/{driverId}/vehicles")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByDriverId(@PathVariable String driverId) {
        List<VehicleResponse> responses = vehicleService.getVehiclesByDriverId(driverId);
        return ResponseEntity.ok(responses);
    }
}
