package com.ridelink.driver_vehicle_service.dto;

import com.ridelink.driver_vehicle_service.model.VehicleStatus;
import com.ridelink.driver_vehicle_service.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class VehicleResponse {

    private String id;

    private String driverId;

    private String registrationNumber;

    private String brand;

    private String model;

    private VehicleType vehicleType;

    private String color;

    private Integer year;

    private VehicleStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
