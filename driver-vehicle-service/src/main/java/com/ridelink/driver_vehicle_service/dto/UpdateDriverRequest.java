package com.ridelink.driver_vehicle_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDriverRequest {

    private String licenseNumber;

    private String serviceArea;
}
