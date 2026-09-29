package com.ridelink.driver_vehicle_service.exception;

public class DuplicateLicenseException extends RuntimeException {

    public DuplicateLicenseException(String licenseNumber) {
        super("Driver with license number " + licenseNumber + " already exists");
    }
}
