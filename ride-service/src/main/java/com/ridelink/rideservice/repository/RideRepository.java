package com.ridelink.rideservice.repository;

import com.ridelink.rideservice.model.Ride;
import com.ridelink.rideservice.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByPassengerId(Long passengerId);

    List<Ride> findByDriverId(Long driverId);

    List<Ride> findByStatus(RideStatus status);
}