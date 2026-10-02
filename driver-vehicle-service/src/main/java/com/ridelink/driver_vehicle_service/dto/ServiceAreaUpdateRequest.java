package com.ridelink.driver_vehicle_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceAreaUpdateRequest {

    @NotBlank(message = "Service area is required")
    private String serviceArea;
}
