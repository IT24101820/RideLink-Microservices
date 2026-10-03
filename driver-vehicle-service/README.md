\# Driver Vehicle Service



This microservice manages driver operational information and vehicle details for the RideLink system.



\## Responsibilities



\- Driver profile management

\- Driver availability management

\- Driver location management

\- Driver service area management

\- Retrieval of available drivers

\- Vehicle registration

\- Vehicle information management

\- Vehicle status management

\- Retrieval of vehicles belonging to a driver



\## Technologies



\- Java

\- Spring Boot

\- Spring Data MongoDB

\- MongoDB

\- Maven

\- REST API



\## Port

The Driver Vehicle Service runs on port:



8082



\## Database



Database Type: MongoDB



Database Name: ridelink\_driver\_vehicle



\## Main Driver Endpoints



POST   /api/drivers  

GET    /api/drivers/{driverId}  

PUT    /api/drivers/{driverId}  

PATCH  /api/drivers/{driverId}/availability  

PATCH  /api/drivers/{driverId}/location  

PATCH  /api/drivers/{driverId}/service-area  

GET    /api/drivers/available  



\## Main Vehicle Endpoints



POST   /api/vehicles  

GET    /api/vehicles/{vehicleId}  

PUT    /api/vehicles/{vehicleId}  

PATCH  /api/vehicles/{vehicleId}/status  

GET    /api/drivers/{driverId}/vehicles  



\## MongoDB Configuration



Before running the service, configure the MongoDB URI:



export MONGODB\_URI='mongodb://127.0.0.1:27017'



\## Run the Service



bash mvnw spring-boot:run



\## Run Tests



bash mvnw clean test



The service includes tests for Driver and Vehicle functionality.

