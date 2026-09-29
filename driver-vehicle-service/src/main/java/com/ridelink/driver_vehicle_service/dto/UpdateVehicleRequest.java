package com.ridelink.driver_vehicle_service.dto;

import com.ridelink.driver_vehicle_service.model.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVehicleRequest {

    private String registrationNumber;

    private String brand;

    private String model;

    private VehicleType vehicleType;

    private String color;

    private Integer year;
}
