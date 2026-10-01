package com.ridelink.driver_vehicle_service.dto;

import com.ridelink.driver_vehicle_service.model.DriverStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityUpdateRequest {

    @NotNull(message = "Availability status is required")
    private DriverStatus availabilityStatus;
}
