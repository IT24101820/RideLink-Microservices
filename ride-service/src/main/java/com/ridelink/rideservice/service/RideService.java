package com.ridelink.rideservice.service;

import com.ridelink.rideservice.dto.DriverAssignmentDto;
import com.ridelink.rideservice.dto.RideRequestDto;
import com.ridelink.rideservice.dto.RideResponseDto;

import java.util.List;

public interface RideService {

    RideResponseDto createRide(RideRequestDto request);

    RideResponseDto getRideById(Long rideId);

    List<RideResponseDto> getRidesByPassengerId(Long passengerId);

    List<RideResponseDto> getRidesByDriverId(Long driverId);

    RideResponseDto assignDriver(Long rideId, DriverAssignmentDto request);

    RideResponseDto acceptRide(Long rideId);

    RideResponseDto startRide(Long rideId);

    RideResponseDto completeRide(Long rideId);

    RideResponseDto cancelRide(Long rideId);
}