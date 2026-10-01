package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.dto.*;
import com.ridelink.driver_vehicle_service.exception.DriverNotFoundException;
import com.ridelink.driver_vehicle_service.exception.DuplicateVehicleRegistrationException;
import com.ridelink.driver_vehicle_service.exception.VehicleNotFoundException;
import com.ridelink.driver_vehicle_service.model.Vehicle;
import com.ridelink.driver_vehicle_service.model.VehicleStatus;
import com.ridelink.driver_vehicle_service.model.VehicleType;
import com.ridelink.driver_vehicle_service.repository.DriverRepository;
import com.ridelink.driver_vehicle_service.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle sampleVehicle;

    @BeforeEach
    void setUp() {
        sampleVehicle = Vehicle.builder()
                .id("vehicle-1")
                .driverId("driver-1")
                .registrationNumber("WP CAB-1234")
                .brand("Toyota")
                .model("Prius")
                .vehicleType(VehicleType.CAR)
                .color("White")
                .year(2022)
                .status(VehicleStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createVehicle_success() {
        CreateVehicleRequest request = new CreateVehicleRequest();
        request.setDriverId("driver-1");
        request.setRegistrationNumber("WP CAB-1234");
        request.setBrand("Toyota");
        request.setModel("Prius");
        request.setVehicleType(VehicleType.CAR);
        request.setColor("White");
        request.setYear(2022);

        when(driverRepository.existsById("driver-1")).thenReturn(true);
        when(vehicleRepository.existsByRegistrationNumber("WP CAB-1234")).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(sampleVehicle);

        VehicleResponse response = vehicleService.createVehicle(request);

        assertNotNull(response);
        assertEquals("vehicle-1", response.getId());
        assertEquals("driver-1", response.getDriverId());
        assertEquals("WP CAB-1234", response.getRegistrationNumber());
        assertEquals(VehicleStatus.ACTIVE, response.getStatus());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void createVehicle_duplicateRegistration() {
        CreateVehicleRequest request = new CreateVehicleRequest();
        request.setDriverId("driver-1");
        request.setRegistrationNumber("WP CAB-1234");

        when(driverRepository.existsById("driver-1")).thenReturn(true);
        when(vehicleRepository.existsByRegistrationNumber("WP CAB-1234")).thenReturn(true);

        assertThrows(DuplicateVehicleRegistrationException.class, () -> vehicleService.createVehicle(request));
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void createVehicle_driverNotFound() {
        CreateVehicleRequest request = new CreateVehicleRequest();
        request.setDriverId("invalid-driver");
        request.setRegistrationNumber("WP CAB-1234");

        when(driverRepository.existsById("invalid-driver")).thenReturn(false);

        assertThrows(DriverNotFoundException.class, () -> vehicleService.createVehicle(request));
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void getVehicleById_success() {
        when(vehicleRepository.findById("vehicle-1")).thenReturn(Optional.of(sampleVehicle));

        VehicleResponse response = vehicleService.getVehicleById("vehicle-1");

        assertNotNull(response);
        assertEquals("vehicle-1", response.getId());
        assertEquals("WP CAB-1234", response.getRegistrationNumber());
    }

    @Test
    void getVehicleById_notFound() {
        when(vehicleRepository.findById("invalid-vehicle")).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.getVehicleById("invalid-vehicle"));
    }

    @Test
    void updateVehicle_success() {
        UpdateVehicleRequest request = new UpdateVehicleRequest();
        request.setBrand("Honda");
        request.setModel("Civic");

        when(vehicleRepository.findById("vehicle-1")).thenReturn(Optional.of(sampleVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(sampleVehicle);

        VehicleResponse response = vehicleService.updateVehicle("vehicle-1", request);

        assertNotNull(response);
        assertEquals("Honda", sampleVehicle.getBrand());
        assertEquals("Civic", sampleVehicle.getModel());
        verify(vehicleRepository, times(1)).save(sampleVehicle);
    }

    @Test
    void updateVehicleStatus_success() {
        VehicleStatusUpdateRequest request = new VehicleStatusUpdateRequest();
        request.setStatus(VehicleStatus.INACTIVE);

        when(vehicleRepository.findById("vehicle-1")).thenReturn(Optional.of(sampleVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(sampleVehicle);

        VehicleResponse response = vehicleService.updateVehicleStatus("vehicle-1", request);

        assertNotNull(response);
        assertEquals(VehicleStatus.INACTIVE, sampleVehicle.getStatus());
        verify(vehicleRepository, times(1)).save(sampleVehicle);
    }

    @Test
    void getVehiclesByDriverId_success() {
        when(driverRepository.existsById("driver-1")).thenReturn(true);
        when(vehicleRepository.findByDriverId("driver-1")).thenReturn(List.of(sampleVehicle));

        List<VehicleResponse> responses = vehicleService.getVehiclesByDriverId("driver-1");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("vehicle-1", responses.get(0).getId());
    }
}
