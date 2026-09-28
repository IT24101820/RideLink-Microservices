package com.ridelink.rideservice.dto;

import com.ridelink.rideservice.model.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RideResponseDto {

    private Long id;
    private Long passengerId;
    private Long driverId;
    private String pickupLocation;
    private String destination;
    private RideStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}