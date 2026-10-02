# RideLink Microservices

Backend microservices implementation for the RideLink system developed for the IT3130 Application Development Group Assignment.

## Project Overview

RideLink is a ride management backend implemented using a microservices architecture.
The system separates account management, ride management, driver and vehicle management, and fare payment processing into independent Spring Boot services.

## Microservices

| Service | Port | Database | Main Responsibility |
|---|---:|---|---|
| Account Service | 8080 | MySQL | User registration, login and account management |
| Ride Service | 8081 | MySQL | Ride creation and ride lifecycle management |
| Driver Vehicle Service | 8082 | MongoDB | Driver profiles, availability and vehicle management |
| Fare Payment Service | 8083 | MongoDB | Fare calculation, fare finalization and payment processing |

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Data MongoDB
- MySQL
- MongoDB
- Maven
- REST APIs
- JWT Authentication
- Git and GitHub
- OpenAPI / Swagger

## Service Structure

```text
RideLink-Microservices/
├── account-service/
├── ride-service/
├── driver-vehicle-service/
├── fare-payment-service/
├── docs/
├── postman/
└── README.md

## Environment Variables

Before running the services, configure the required environment variables.

### Account Service

```bash
export DB_PASSWORD='your_mysql_password'
export JWT_SECRET='your_jwt_secret'
```

Database: `ridelink_account_db`

### Ride Service

```bash
export DB_PASSWORD='your_mysql_password'
```

Database: `ridelink_ride_db`

### Driver Vehicle Service

```bash
export MONGODB_URI='mongodb://127.0.0.1:27017'
```

Database: `ridelink_driver_vehicle`

### Fare Payment Service

```bash
export MONGODB_URI='mongodb://127.0.0.1:27017'
```

Database: `ridelink_fare_payment`

## Running the Services

Each microservice should be started in a separate terminal.

```bash
cd account-service
bash mvnw spring-boot:run
```

```bash
cd ride-service
bash mvnw spring-boot:run
```

```bash
cd driver-vehicle-service
bash mvnw spring-boot:run
```

```bash
cd fare-payment-service
bash mvnw spring-boot:run
```

The services run on the following ports:

- Account Service: `8080`
- Ride Service: `8081`
- Driver Vehicle Service: `8082`
- Fare Payment Service: `8083`

## End-to-End System Flow

```text
Passenger Registration
        ↓
Driver Registration
        ↓
Driver Profile Creation
        ↓
Vehicle Registration
        ↓
Driver Availability
        ↓
Ride Request
        ↓
Driver Assignment
        ↓
Ride Acceptance
        ↓
Ride Start
        ↓
Ride Completion
        ↓
Fare Estimation
        ↓
Fare Finalization
        ↓
Payment Processing
```

## Ride Lifecycle

```text
REQUESTED
   ↓
ASSIGNED
   ↓
ACCEPTED
   ↓
IN_PROGRESS
   ↓
COMPLETED
```

## Fare and Payment

The Fare Payment Service supports fare estimation, fare finalization, CASH and CARD payment methods, payment retrieval and transaction reference generation.

Example fare calculation:

```text
Base Fare      = 200
Distance Fare  = Distance × 100
Time Fare      = Duration × 20

Final Fare = Base Fare + Distance Fare + Time Fare
```

## Testing

All four microservices were successfully started and tested.

The complete end-to-end integration flow was verified from passenger registration through driver and vehicle creation, ride lifecycle processing, fare calculation and final payment processing.

Individual Maven tests were also executed for the services.

## Git Workflow

Development was performed using separate feature branches for each microservice.

Completed feature branches were merged into the `main` branch using GitHub Pull Requests.

## Project Status

The core RideLink backend microservices have been integrated into the `main` branch and the complete business flow has been successfully tested.
