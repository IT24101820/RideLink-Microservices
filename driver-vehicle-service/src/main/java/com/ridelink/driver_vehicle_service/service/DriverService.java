package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.dto.*;

import java.util.List;

public interface DriverService {

    DriverResponse createDriver(CreateDriverRequest request);

    DriverResponse getDriverById(String id);

    DriverResponse updateDriver(String id, UpdateDriverRequest request);

    DriverResponse updateAvailability(String id, AvailabilityUpdateRequest request);

    DriverResponse updateLocation(String id, LocationUpdateRequest request);

    DriverResponse updateServiceArea(String id, ServiceAreaUpdateRequest request);

    List<DriverResponse> getAvailableDrivers();

    List<DriverResponse> getAvailableDriversByServiceArea(String serviceArea);
}
