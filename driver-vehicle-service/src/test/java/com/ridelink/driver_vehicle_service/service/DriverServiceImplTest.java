package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.dto.*;
import com.ridelink.driver_vehicle_service.exception.DriverNotFoundException;
import com.ridelink.driver_vehicle_service.exception.DuplicateLicenseException;
import com.ridelink.driver_vehicle_service.model.Driver;
import com.ridelink.driver_vehicle_service.model.DriverStatus;
import com.ridelink.driver_vehicle_service.repository.DriverRepository;
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
class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceImpl driverService;

    private Driver sampleDriver;

    @BeforeEach
    void setUp() {
        sampleDriver = Driver.builder()
                .id("driver-1")
                .accountId("acc-1")
                .licenseNumber("LIC-100")
                .serviceArea("Colombo")
                .availabilityStatus(DriverStatus.AVAILABLE)
                .latitude(6.9271)
                .longitude(79.8612)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createDriver_success() {
        CreateDriverRequest request = new CreateDriverRequest();
        request.setAccountId("acc-1");
        request.setLicenseNumber("LIC-100");
        request.setServiceArea("Colombo");

        when(driverRepository.existsByLicenseNumber("LIC-100")).thenReturn(false);
        when(driverRepository.save(any(Driver.class))).thenReturn(sampleDriver);

        DriverResponse response = driverService.createDriver(request);

        assertNotNull(response);
        assertEquals("driver-1", response.getId());
        assertEquals("acc-1", response.getAccountId());
        assertEquals("LIC-100", response.getLicenseNumber());
        assertEquals("Colombo", response.getServiceArea());
        verify(driverRepository, times(1)).save(any(Driver.class));
    }

    @Test
    void createDriver_duplicateLicense() {
        CreateDriverRequest request = new CreateDriverRequest();
        request.setAccountId("acc-1");
        request.setLicenseNumber("LIC-100");
        request.setServiceArea("Colombo");

        when(driverRepository.existsByLicenseNumber("LIC-100")).thenReturn(true);

        assertThrows(DuplicateLicenseException.class, () -> driverService.createDriver(request));
        verify(driverRepository, never()).save(any(Driver.class));
    }

    @Test
    void getDriverById_success() {
        when(driverRepository.findById("driver-1")).thenReturn(Optional.of(sampleDriver));

        DriverResponse response = driverService.getDriverById("driver-1");

        assertNotNull(response);
        assertEquals("driver-1", response.getId());
        assertEquals("LIC-100", response.getLicenseNumber());
    }

    @Test
    void getDriverById_notFound() {
        when(driverRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> driverService.getDriverById("invalid-id"));
    }

    @Test
    void updateAvailability_success() {
        AvailabilityUpdateRequest request = new AvailabilityUpdateRequest();
        request.setAvailabilityStatus(DriverStatus.UNAVAILABLE);

        when(driverRepository.findById("driver-1")).thenReturn(Optional.of(sampleDriver));
        when(driverRepository.save(any(Driver.class))).thenReturn(sampleDriver);

        DriverResponse response = driverService.updateAvailability("driver-1", request);

        assertNotNull(response);
        assertEquals(DriverStatus.UNAVAILABLE, sampleDriver.getAvailabilityStatus());
        verify(driverRepository, times(1)).save(sampleDriver);
    }

    @Test
    void updateLocation_success() {
        LocationUpdateRequest request = new LocationUpdateRequest();
        request.setLatitude(7.2906);
        request.setLongitude(80.6337);

        when(driverRepository.findById("driver-1")).thenReturn(Optional.of(sampleDriver));
        when(driverRepository.save(any(Driver.class))).thenReturn(sampleDriver);

        DriverResponse response = driverService.updateLocation("driver-1", request);

        assertNotNull(response);
        assertEquals(7.2906, sampleDriver.getLatitude());
        assertEquals(80.6337, sampleDriver.getLongitude());
        verify(driverRepository, times(1)).save(sampleDriver);
    }

    @Test
    void getAvailableDrivers_success() {
        when(driverRepository.findByAvailabilityStatus(DriverStatus.AVAILABLE))
                .thenReturn(List.of(sampleDriver));

        List<DriverResponse> responses = driverService.getAvailableDrivers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("driver-1", responses.get(0).getId());
    }

    @Test
    void getAvailableDriversByServiceArea_success() {
        when(driverRepository.findByAvailabilityStatusAndServiceArea(DriverStatus.AVAILABLE, "Colombo"))
                .thenReturn(List.of(sampleDriver));

        List<DriverResponse> responses = driverService.getAvailableDriversByServiceArea("Colombo");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Colombo", responses.get(0).getServiceArea());
    }
}
