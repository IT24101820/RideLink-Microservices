package com.ridelink.driver_vehicle_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    private String id;

    private String accountId;

    private String licenseNumber;

    private String serviceArea;

    @Builder.Default
    private DriverStatus availabilityStatus = DriverStatus.OFFLINE;

    private Double latitude;

    private Double longitude;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
