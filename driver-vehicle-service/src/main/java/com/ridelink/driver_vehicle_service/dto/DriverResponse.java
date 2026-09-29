package com.ridelink.driver_vehicle_service.dto;

import com.ridelink.driver_vehicle_service.model.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DriverResponse {

    private String id;

    private String accountId;

    private String licenseNumber;

    private String serviceArea;

    private DriverStatus availabilityStatus;

    private Double latitude;

    private Double longitude;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
