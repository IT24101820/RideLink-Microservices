package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.dto.*;

import java.util.List;

public interface VehicleService {

    VehicleResponse createVehicle(CreateVehicleRequest request);

    VehicleResponse getVehicleById(String id);

    List<VehicleResponse> getVehiclesByDriverId(String driverId);

    VehicleResponse updateVehicle(String id, UpdateVehicleRequest request);

    VehicleResponse updateVehicleStatus(String id, VehicleStatusUpdateRequest request);
}
