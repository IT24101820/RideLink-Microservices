package com.ridelink.driver_vehicle_service.exception;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(String id) {
        super("Vehicle not found with id: " + id);
    }
}
