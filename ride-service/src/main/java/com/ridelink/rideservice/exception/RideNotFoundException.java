package com.ridelink.rideservice.exception;

public class RideNotFoundException extends RuntimeException {

    public RideNotFoundException(Long rideId) {
        super("Ride not found with ID: " + rideId);
    }
}