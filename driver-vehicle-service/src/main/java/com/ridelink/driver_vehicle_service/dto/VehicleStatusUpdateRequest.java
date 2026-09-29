package com.ridelink.driver_vehicle_service.dto;

import com.ridelink.driver_vehicle_service.model.VehicleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleStatusUpdateRequest {

    @NotNull(message = "Vehicle status is required")
    private VehicleStatus status;
}
