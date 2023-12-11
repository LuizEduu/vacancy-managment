# Vacancy Management System

## Description

This Spring Boot project manages companies, candidates, and job vacancies, allowing candidates to apply for available positions.

## Prerequisites

Ensure the following are installed before running the application:

- Java 21
- PostgreSQL
- Docker (optional, for building and packaging)

## Getting Started

Clone the repository to your local machine:

git clone https://github.com/your-username/vacancy-management.git
cd vacancy-management

Dependencies
The project uses the following dependencies:

- Spring Boot Starter Validation
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot DevTools
- Lombok
- Spring Boot Starter Test
- PostgreSQL
- Java JWT
- SpringDoc OpenAPI Starter
- JUnit Jupiter
- Spring Security Test
- Spring Boot Starter Actuator
- H2 Database (for testing)
- Micrometer Registry Prometheus

Build and Run
Build and run the project using Maven:

mvn clean install
java -jar target/vacancy_management-0.0.1-SNAPSHOT.jar

Alternatively, use Docker for building and packaging:

docker build -t vacancy-management .
docker run -p 8080:8080 vacancy-management

Access the application at http://localhost:8080.

API Documentation
The API documentation is available using Swagger UI. Once the application is running, navigate to:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


Testing
Run the tests using:

mvn test

Contributing
Feel free to contribute to this project. Create a pull request with your changes or open an issue for any bugs or feature requests.



