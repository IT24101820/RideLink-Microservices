package com.ridelink.rideservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverAssignmentDto {

    @NotNull(message = "Driver ID is required")
    private String driverId;
}