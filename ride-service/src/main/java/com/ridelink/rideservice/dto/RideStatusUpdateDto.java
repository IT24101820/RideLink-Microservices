package com.ridelink.rideservice.dto;

import com.ridelink.rideservice.model.RideStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RideStatusUpdateDto {

    @NotNull(message = "Ride status is required")
    private RideStatus status;
}