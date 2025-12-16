# ProfileVit[AI]e

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Build](https://img.shields.io/badge/build-passing-success)
![Tests](https://img.shields.io/badge/tests-JUnit%205%20%7C%20Mockito-blue)
![License](https://img.shields.io/badge/license-MIT-blue)
![Status](https://img.shields.io/badge/status-active%20development-yellow)
![Architecture](https://img.shields.io/badge/architecture-clean%20architecture-purple)

---

## 📦 Version
**v1.0.0** – Initial stable backend release

## 📄 License
This project is licensed under the **MIT License**.  
See the `LICENSE` file for details.

## 🏗 Project Status
🚧 **Active Development**  
New features, AI improvements and security enhancements are continuously being added.

---

## Project Summary 📚

ProfileVit[AI]e is an intelligent web platform that uses AI to help tech professionals grow their careers. It analyzes your skills and experience to create a personalized growth roadmap and suggest better job opportunities.

---

## 📌 Overview

Profile Vitaile Backend is a Spring Boot REST API that handles:

- Secure user authentication and profile management

- CV (PDF) upload and text extraction

- AI-powered analysis to generate:

  - Personalized career roadmaps

  - Project recommendations

  - Professional summaries and employability insights

- Observability, security and production-ready best practices

The system is designed following clean architecture principles, with strong emphasis on testability, scalability and security.

--- 
## 🧠 Key Features
### 🔐 Authentication & Security

- JWT-based authentication

- Spring Security filter chain

- Role-based access control (FREE / PREMIUM / ADMIN)

- Custom JWT Authentication Filter

- Secure password hashing (BCrypt)

### 👤 User Profile Management

- User registration & login

- Profile completion

- Experience, education and skills stored as JSON

- Resume (CV) upload and analysis

### 📄 CV Processing

- PDF upload support

- Text extraction using Apache PDFBox

- Structured CV analysis pipeline

- AI-ready extraction layer

### 🤖 AI Integration

- CV content sent to AI service

Generates:

- Career roadmap

- Suggested projects

- Professional summary

- Employability feedback

- Clean separation between AI logic and core domain

### 📊 Observability & Monitoring

- Spring Boot Actuator

- Prometheus metrics endpoint

Structured logging with MDC:

- traceId

- userId

- endpoint

### 🧪 Testing Strategy

- Unit tests (services, security, JWT)

- Integration tests (controllers, repositories, security)

- H2 in-memory database for tests

- MockMvc for HTTP layer testing

---
## 🏗 Architecture
    The project follows a layered clean architecture:
    src/main/java
    └── showtime_corp.profile_vitaile
    ├── controller        # REST controllers
    ├── service           # Business logic
    ├── repository        # JPA repositories
    ├── entity            # JPA entities
    ├── dto               # Request / Response DTOs
    ├── mapper            # MapStruct mappers
    ├── security          # Spring Security & JWT
    │    └── jwt
    ├── logging           # TraceId / MDC filters
    ├── exception         # Global & custom exceptions
    └── config            # OpenAPI, Security, Beans

--- 
## 💻 Technologies Used 

*   **Backend:** Spring Boot
*   **Frontend:** Angular
*   **Database:** MySQL
*   **Authentication:** Spring Security


## 🛠 Tech Stack

- Java 21

- Spring Boot

- Spring Security

- JWT (jjwt)

- Hibernate / JPA

- MapStruct

- Flyway

- H2 / PostgreSQL

- JUnit 5 & Mockito

- MockMvc

- Apache PDFBox

- Actuator & Prometheus

- OpenAPI / Swagger

---

## 🔑 Authentication Flow

1. User registers (/auth/register)

2. User logs in (/auth/login)

3. Backend issues JWT

4. JWT is required in protected endpoints:

```
Authorization: Bearer <JWT_TOKEN>
```
--- 
## 📚 API Documentation

Swagger UI available at:
```
/swagger-ui.html
```

--- 

## 🧪 Tests
Run tests
```
mvn test
```
Test coverage includes:

- AuthService (unit)

- JwtService (unit)

- UserRepository (integration)

- Auth & Profile Controllers (integration)

- Security filters behavior

--- 
## 📦 Database Migrations

Database schema managed using Flyway
```
mvn flyway:migrate
```
--- 
## Quick Start

1.  **Set up the database:**
    *   Make sure you have MySQL installed.
    *   Create a database named `profile_vitaile`.
    *   Update the database credentials in `src/main/resources/application.properties`.

2.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
--- 
## 👨‍💻 Author
- Jose Manuel Granados Frontend Developer 
[LinkedIn](https://www.linkedin.com/in/jose-manuel-granados-frontend-developer/)
[GitHub](https://github.com/jmgrndos)
- Ximena guerrero Backend Developer 
[LinkedIn](https://www.linkedin.com/in/jose-manuel-granados-backend-developer/)
[GitHub](https://github.com/xguerrerov0903)
- Andres niebles Backend Developer - Scrum master
[LinkedIn](https://www.linkedin.com/in/jose-manuel-granados-backend-developer/)
[GitHub](https://github.com/AndresN329)
- Mateo Algarin IA Backend Developer - Product Owner 
[LinkedIn](https://www.linkedin.com/in/jose-manuel-granados-backend-developer/)
[GitHub](https://github.com/MateoAlRen)
- Santiago Toro
Security and QA – Java & Spring Boot
[LinkedIn](https://www.linkedin.com/in/santiagotoro/)
[GitHub](https://github.com/SantiagoToroMesa)
- Samuel Monsalve Backend Developer
[LinkedIn](https://www.linkedin.com/in/samuel-monsalve-9777a81a2/)
[GitHub](https://github.com/SamuelMonsalve)