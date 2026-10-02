package com.ridelink.rideservice.service;

import com.ridelink.rideservice.dto.DriverAssignmentDto;
import com.ridelink.rideservice.dto.RideRequestDto;
import com.ridelink.rideservice.dto.RideResponseDto;
import com.ridelink.rideservice.exception.InvalidRideStatusException;
import com.ridelink.rideservice.exception.RideNotFoundException;
import com.ridelink.rideservice.model.Ride;
import com.ridelink.rideservice.model.RideStatus;
import com.ridelink.rideservice.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public RideResponseDto createRide(RideRequestDto request) {

        Ride ride = Ride.builder()
                .passengerId(request.getPassengerId())
                .pickupLocation(request.getPickupLocation())
                .destination(request.getDestination())
                .status(RideStatus.REQUESTED)
                .build();

        Ride savedRide = rideRepository.save(ride);

        return mapToDto(savedRide);
    }

    @Override
    public RideResponseDto getRideById(Long rideId) {
        return mapToDto(getRide(rideId));
    }

    @Override
    public List<RideResponseDto> getRidesByPassengerId(Long passengerId) {
        return rideRepository.findByPassengerId(passengerId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<RideResponseDto> getRidesByDriverId(String driverId) {
        return rideRepository.findByDriverId(driverId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public RideResponseDto assignDriver(
            Long rideId,
            DriverAssignmentDto request) {

        Ride ride = getRide(rideId);

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new InvalidRideStatusException(
                    "Driver can only be assigned when ride status is REQUESTED"
            );
        }

        ride.setDriverId(request.getDriverId());
        ride.setStatus(RideStatus.ASSIGNED);

        return mapToDto(rideRepository.save(ride));
    }

    @Override
    public RideResponseDto acceptRide(Long rideId) {

        Ride ride = getRide(rideId);

        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new InvalidRideStatusException(
                    "Ride can only be accepted when status is ASSIGNED"
            );
        }

        ride.setStatus(RideStatus.ACCEPTED);

        return mapToDto(rideRepository.save(ride));
    }

    @Override
    public RideResponseDto startRide(Long rideId) {

        Ride ride = getRide(rideId);

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new InvalidRideStatusException(
                    "Ride can only be started when status is ACCEPTED"
            );
        }

        ride.setStatus(RideStatus.IN_PROGRESS);

        return mapToDto(rideRepository.save(ride));
    }

    @Override
    public RideResponseDto completeRide(Long rideId) {

        Ride ride = getRide(rideId);

        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new InvalidRideStatusException(
                    "Ride can only be completed when status is IN_PROGRESS"
            );
        }

        ride.setStatus(RideStatus.COMPLETED);

        return mapToDto(rideRepository.save(ride));
    }

    @Override
    public RideResponseDto cancelRide(Long rideId) {

        Ride ride = getRide(rideId);

        if (ride.getStatus() == RideStatus.IN_PROGRESS ||
                ride.getStatus() == RideStatus.COMPLETED ||
                ride.getStatus() == RideStatus.CANCELLED) {

            throw new InvalidRideStatusException(
                    "Ride cannot be cancelled when status is "
                            + ride.getStatus()
            );
        }

        ride.setStatus(RideStatus.CANCELLED);

        return mapToDto(rideRepository.save(ride));
    }

    private Ride getRide(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RideNotFoundException(rideId)
                );
    }

    private RideResponseDto mapToDto(Ride ride) {

        return RideResponseDto.builder()
                .id(ride.getId())
                .passengerId(ride.getPassengerId())
                .driverId(ride.getDriverId())
                .pickupLocation(ride.getPickupLocation())
                .destination(ride.getDestination())
                .status(ride.getStatus())
                .createdAt(ride.getCreatedAt())
                .updatedAt(ride.getUpdatedAt())
                .build();
    }
}