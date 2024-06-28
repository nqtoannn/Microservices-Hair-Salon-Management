# Hair Salon Management System 🍃☁️

This project demonstrates a microservices architecture for managing a hair salon using Spring Boot, Spring Cloud, and Netflix Eureka. The application consists of multiple microservices, each responsible for a specific business functionality, such as managing appointments, customers, services, and staff. These microservices register with Eureka for service discovery.

## Table of Contents
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Microservices](#microservices)
- [Dependencies](#dependencies)
- [Dockerization](#Dockerization)
- [Troubleshooting](#troubleshooting)

## Architecture

The architecture consists of the following components:

- **Eureka Server**: Service discovery server.
- **API Gateway**: Routing and filtering using Spring Cloud Gateway.
- **Microservices**: Hairservice, Orderservice, Appointmentservice, Productservice, Userservice.
- **Axon Server**
- **MySQL Database**: Database for data persistence.

## Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Docker (optional, for containerization)
- Postman (optional, for API testing)

## Getting Started

Clone the repository:

```bash
git clone [https://github.com/nqtoannn/Microservices-Hair-Salon-Management](https://github.com/nqtoannn/Microservices-Hair-Salon-Management)
cd hair-salon-management
```
## Running the Application
1. **Start MySQL Database**: 
  - Ensure MySQL Server is running.
  - Create a new database schema for Hairservice, Orderservice, Appointmentservice, Productservice, Userservice.

2. **Start Eureka Server**:
  - cd discoveryserver
  - mvn clean install
  - mvn spring-boot:run

3. **Start API Gateway**:
  - cd apigateway
  - mvn clean install
  - mvn spring-boot:run
4. **Setup AxonServer**:
  - Download and install AxonServer if you haven't already.
  - Start AxonServer by running the following command:
    ```
      java -jar axonserver.jar
    ```
  - Access AxonServer Dashboard in your browser to monitor and manage Axon components:
    ```
      http://localhost:8024
    ```
5. **Start Microservices**:
  For each microservice 
  - cd microservice
  - mvn clean install
  - mvn spring-boot:run

6. **Configure Microservices with MySQL**:
   - Edit the `application.properties` or `application.yml` file in each microservice directory to configure MySQL connection details. Example configuration:
     ```yaml
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/salon_db?useSSL=false&serverTimezone=UTC
         username: root
         password: rootpassword
     ```
     Replace `salon_db`, `root`, and `rootpassword` with your MySQL database name, username, and password.

7. **Access Eureka Dashboard**:
   - Open Eureka Dashboard in your browser to view registered microservices:
     ```
       http://localhost:8761
     ```

8. **Testing with Postman**:
    - Import the provided Postman collection (`Microservice App.postman_collection.json`) for testing API endpoints.

## Microservices

- **Eureka Server**: `eureka-server`
- **API Gateway**: `api-gateway`
- **Appointment Service**: `appointment-service`
- **User Service**: `user-service`
- **Product Service**: `product-service`
- **Order Service**: `order-service`
- **HairService Service**: `hairservice-service`
- **Common Service**: `common-service`

## Dependencies
The project uses the following dependencies:
- Spring Boot
- Spring Cloud
- Netflix Eureka
- Spring Cloud Config
- Spring Cloud Gateway
- Spring Data JPA
- MySQL Connector
- Docker
## Dockerization
Build and run containers for all services in DockerFile
Ex:
  - cd microservice
  - docker build -t microservice:0.0.1 .
  - docker run -d -p 8084:8084 microservice:0.0.1
Adjust port numbers as necessary.
## Troubleshooting
If you encounter any issues, please check the following:
  - Ensure MySQL is running and the database schema is created.
  - Verify that Eureka Server and Config Server are up and running.
  - Check the logs for any error messages.
