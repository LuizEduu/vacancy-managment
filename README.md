
Vacancy Management System
Description
This is a Spring Boot project for managing companies, candidates, and job vacancies. The system allows candidates to apply for available job positions.

Table of Contents
Prerequisites
Getting Started
Dependencies
Build and Run
API Documentation
Testing
Contributing
License
Prerequisites
Make sure you have the following installed before running the application:

Java 21
PostgreSQL
Docker (optional, for building and packaging)
Getting Started
Clone the repository to your local machine:

bash
Copy code
git clone https://github.com/your-username/vacancy-management.git
cd vacancy-management
Dependencies
The project uses the following dependencies:

Spring Boot Starter Validation
Spring Boot Starter Data JPA
Spring Boot Starter Web
Spring Boot Starter Security
Spring Boot DevTools
Lombok
Spring Boot Starter Test
PostgreSQL
Java JWT
SpringDoc OpenAPI Starter
JUnit Jupiter
Spring Security Test
Spring Boot Starter Actuator
H2 Database (for testing)
Micrometer Registry Prometheus
Build and Run
You can build and run the project using Maven:

bash
Copy code
mvn clean install
java -jar target/vacancy_management-0.0.1-SNAPSHOT.jar
Alternatively, you can use Docker for building and packaging:

bash
Copy code
docker build -t vacancy-management .
docker run -p 8080:8080 vacancy-management
The application will be accessible at http://localhost:8080.

API Documentation
The API documentation is available using Swagger UI. Once the application is running, navigate to:

http://localhost:8080/swagger-ui.html

Testing
Run the tests using:

bash
Copy code
mvn test
Contributing
Feel free to contribute to this project. Create a pull request with your changes, or open an issue for any bugs or feature requests.
