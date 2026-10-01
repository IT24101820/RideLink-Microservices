package com.ridelink.driver_vehicle_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI driverVehicleServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Driver & Vehicle Service API")
                        .description("REST API documentation for RideLink Driver and Vehicle Microservice")
                        .version("1.0.0"));
    }
}
