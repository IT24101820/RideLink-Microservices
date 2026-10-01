package com.ridelink.driver_vehicle_service.exception;

public class DuplicateVehicleRegistrationException extends RuntimeException {

    public DuplicateVehicleRegistrationException(String registrationNumber) {
        super("Vehicle with registration number " + registrationNumber + " already exists");
    }
}
