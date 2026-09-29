package com.ridelink.driver_vehicle_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    private String id;

    private String driverId;

    private String registrationNumber;

    private String brand;

    private String model;

    private VehicleType vehicleType;

    private String color;

    private Integer year;

    @Builder.Default
    private VehicleStatus status = VehicleStatus.ACTIVE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
