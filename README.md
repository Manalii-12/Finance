# Finance Dashboard Backend 🚀

A secure and robust backend system for a Finance Dashboard built with **Java, Spring Boot, and H2**. This API provides a comprehensive backend solution for tracking financial records, managing user access, and viewing high-level dashboard analytics.

## 🛠️ Tech Stack
- **Java 17** & **Spring Boot 3**
- **Spring Security & JWT** (JSON Web Tokens) for Authentication 
- **Spring Data JPA** & **Hibernate** for ORM
- **H2 Database** (File-based local persistence)
- **Maven** for dependency management

## ✨ Core Features
- **Role-Based Access Control (RBAC):** Differentiated access for `VIEWER`, `ANALYST`, and `ADMIN` roles.
- **User Module:** Complete CRUD operations for User management.
- **Financial Records Module:** Secure endpoints for submitting and managing financial entries.
- **Dashboard Analytics:** High-level API to serve visualization metrics.
- **Robust Error Handling:** Centralized `GlobalExceptionHandler` ensures clean, readable JSON error responses (e.g., catching malformed inputs and duplicate records instantly).

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.x

### Running Locally
1. Clone the repository
2. Navigate to the project root directory
3. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
4. The server will start on `http://localhost:8080`.

### Database
The database uses H2 and saves locally in the `/data` folder. You can access the H2 console by navigating to `http://localhost:8080/h2-console` (see `application.properties` for credentials).

## 🔒 Authentication Flow
To hit protected endpoints (like `/api/users`), you first need a valid JWT token:
1. Hit `POST /api/auth/register` to create a new user.
2. The response will return a Bearer Token.
3. Attach this token in the `Authorization` header as `Bearer <your_token>` in all subsequent requests.
