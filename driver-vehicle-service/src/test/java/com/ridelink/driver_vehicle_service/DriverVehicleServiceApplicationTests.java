package com.ridelink.driver_vehicle_service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Integration test requiring MongoDB")
@SpringBootTest
class DriverVehicleServiceApplicationTests {

	@Test
	@Disabled("Requires active MongoDB instance")
	void contextLoads() {
	}

}
