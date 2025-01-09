# Product Service

## Overview
Product Service is a Spring Boot application that provides RESTful APIs for managing products. It integrates with a MySQL database, Redis for caching, and Eureka for service discovery.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Redis
- Eureka
- Flyway
- Maven

## Prerequisites
- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 8.0 or higher
- Redis 6.0 or higher
- Eureka Server

## Setup Instructions

### Database Setup
1. Create a MySQL database:
    ```sql
    CREATE DATABASE productservice;
    ```

2. Update the `application.properties` file with your database credentials:
    ```ini
    spring.datasource.url=jdbc:mysql://localhost:3306/productservice
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    ```

### Redis Setup
1. Ensure Redis is running on `localhost:6379` or update the `application.properties` file with your Redis configuration:
    ```ini
    spring.data.redis.host=localhost
    spring.data.redis.port=6379
    ```

### Eureka Setup
1. Ensure Eureka Server is running and update the `application.properties` file with your Eureka server URL:
    ```ini
    eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
    ```

### Build and Run
1. Build the project using Maven:
    ```sh
    mvn clean install
    ```

2. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## API Endpoints

### Product Endpoints
- **Create Product**
    ```http
    POST /products
    ```
  Request Body: `CreateProductRequestDto`

- **Get All Products**
    ```http
    GET /products
    ```

- **Get Product by ID**
    ```http
    GET /products/{id}
    ```

- **Delete Product**
    ```http
    DELETE /products/{id}
    ```

- **Update Product**
    ```http
    PATCH /products/{id}
    ```
  Request Body: `CreateProductDto`

- **Get Product by Name**
    ```http
    GET /products/name/{name}
    ```

## Configuration
Configuration properties are located in the `application.properties` file.

## License
This project is licensed under the MIT License.