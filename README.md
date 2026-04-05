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

## 🔐 Role Permissions

The API enforces strict Role-Based Access Control (RBAC). Below is the permissions matrix for each role:

| Endpoint Resource | `ADMIN` | `ANALYST` | `VIEWER` | Notes |
| :--- | :---: | :---: | :---: | :--- |
| **Authentication** | ✅ | ✅ | ✅ | Open to all for registration/login. |
| **Dashboard Analytics** | ✅ | ✅ | ✅ | Read-only statistical data. |
| **View Records** | ✅ | ✅ | ✅ | Everyone can fetch records. |
| **Create/Update Records** | ✅ | ✅ | ❌ | `VIEWER`s cannot modify records. |
| **Delete Records** | ✅ | ❌ | ❌ | Only `ADMIN`s can delete records. |
| **User Management** | ✅ | ❌ | ❌ | Only `ADMIN`s can view, modify, or delete other users. |

## 🌐 API Endpoints

### Authentication
- `POST /api/auth/register`: Register a new user (`ADMIN`, `ANALYST`, or `VIEWER`).
- `POST /api/auth/login`: Authenticate and receive a JWT token.

### User Management
- `POST /api/users`: Create a new user account.
- `GET /api/users`: Retrieve a list of all system users.
- `PUT /api/users/{id}`: Update user details.
- `PATCH /api/users/{id}/role`: Update a user's role.
- `DELETE /api/users/{id}`: Delete a user by their ID.

### Financial Records
- `POST /api/records`: Submit a new financial record.
- `GET /api/records`: Fetch all financial records.
- `GET /api/records/{id}`: Fetch a specific financial record.
- `PUT /api/records/{id}`: Update an existing record.
- `DELETE /api/records/{id}`: Delete a record.

### Dashboard
- `GET /api/dashboard/summary`: Retrieve overall financial analytics (e.g., total entries, total amount, recent transactions).

## 📝 Sample Request

Here is how to structure a request to create a new user (Requires `ADMIN` JWT token).

**Request URL:** `POST http://localhost:8080/api/users`  
**Headers:** 
- `Content-Type: application/json`
- `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...`

**Raw JSON Body:**
```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "password": "SecurePassword123",
  "role": "ANALYST",
  "status": "ACTIVE"
}
```

**Expected Status Code:** `201 Created`
