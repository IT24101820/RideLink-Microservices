# Account Service



This microservice manages user accounts, authentication, roles, profiles and account status for the RideLink system.



## Responsibilities



- Passenger account registration

- Driver account registration

- User login

- JWT token generation

- Role management

- User profile retrieval

- User profile updating

- Password updating

- Account status management

- User account deletion

- Retrieval of registered users



## User Roles



- PASSENGER

- DRIVER

- ADMIN



## Technologies



- Java

- Spring Boot

- Spring Data JPA

- MySQL

- Maven

- REST API

- JWT Authentication



## Port



The Account Service runs on port:



8080



## Database



Database Type: MySQL



Database Name:



ridelink_account_db



## Main API Endpoints



POST   /api/users/register

POST   /api/users/login

GET    /api/users/{id}

PUT    /api/users/{id}

PUT    /api/users/{id}/password

GET    /api/users

PUT    /api/users/{id}/status

DELETE /api/users/{id}



## Environment Variables



Before running the service, configure the required environment variables.



Example:



export DB_PASSWORD='YOUR_MYSQL_PASSWORD'

export JWT_SECRET='YOUR_DEVELOPMENT_JWT_SECRET'



Do not commit passwords or secret values to GitHub.



## Run the Service



bash mvnw spring-boot:run



## Run Tests



bash mvnw clean test



The Account Service includes tests for account and user service functionality.


