package com.ridelink.rideservice.controller;

import com.ridelink.rideservice.dto.DriverAssignmentDto;
import com.ridelink.rideservice.dto.RideRequestDto;
import com.ridelink.rideservice.dto.RideResponseDto;
import com.ridelink.rideservice.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public ResponseEntity<RideResponseDto> createRide(
            @Valid @RequestBody RideRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rideService.createRide(request));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideResponseDto> getRideById(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideService.getRideById(rideId)
        );
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<RideResponseDto>> getPassengerRides(
            @PathVariable Long passengerId) {

        return ResponseEntity.ok(
                rideService.getRidesByPassengerId(passengerId)
        );
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<RideResponseDto>> getDriverRides(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                rideService.getRidesByDriverId(driverId)
        );
    }

    @PatchMapping("/{rideId}/assign")
    public ResponseEntity<RideResponseDto> assignDriver(
            @PathVariable Long rideId,
            @Valid @RequestBody DriverAssignmentDto request) {

        return ResponseEntity.ok(
                rideService.assignDriver(rideId, request)
        );
    }

    @PatchMapping("/{rideId}/accept")
    public ResponseEntity<RideResponseDto> acceptRide(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideService.acceptRide(rideId)
        );
    }

    @PatchMapping("/{rideId}/start")
    public ResponseEntity<RideResponseDto> startRide(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideService.startRide(rideId)
        );
    }

    @PatchMapping("/{rideId}/complete")
    public ResponseEntity<RideResponseDto> completeRide(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideService.completeRide(rideId)
        );
    }

    @PatchMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponseDto> cancelRide(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideService.cancelRide(rideId)
        );
    }
}