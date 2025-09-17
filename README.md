# Task Management System (Spring Boot)

A simple Task Management REST API built with Spring Boot. It provides endpoints to create, read, update, and delete tasks, along with basic filtering and status updates. This repository is suitable as a learning project or a starter for a production-ready service.

## Features
- Create, read, update, delete (CRUD) tasks
- Mark tasks as completed / in-progress / pending
- Optional due date and priority fields
- Validation for common fields
- Exception handling with meaningful error responses
- In-memory database by default (e.g., H2) or configurable external DB
- JWT-based authentication with access and refresh tokens

## Tech Stack
- Java 17+ (works with any LTS; adjust as needed)
- Spring Boot (Web, Validation, Data JPA)
- Spring Security (JWT auth)
- H2 (dev) or MySQL/PostgreSQL (optional)
- Maven build tool
- JUnit and Spring Test

## Project Structure
- `src/main/java` — application code (controllers, services, repositories, models)
- `src/main/resources` — configuration (`application.properties`), schema/data seeds if any
- `src/test/java` — unit and integration tests
- `pom.xml` — Maven project definition
- `postman/` — optional Postman collection to try endpoints

## Getting Started

### Prerequisites
- Java JDK 17+ installed and on PATH
- Maven 3.8+ (or use the included Maven Wrapper `mvnw`/`mvnw.cmd`)

Verify versions:
```bash
java -version
mvn -v
```

### Clone
```bash
git clone https://github.com/Bahaa-KhaledDesoky/simple-task-management-system.git
cd task-management-system
```

### Run (Dev)
Using Maven Wrapper (recommended on Windows PowerShell):
```bash
./mvnw spring-boot:run
```
Or with system Maven:
```bash
mvn spring-boot:run
```

By default the app starts on `http://localhost:8080` (configurable via `server.port`).

### Build a Jar
```bash
mvn clean package -DskipTests
java -jar target/*.jar
```

### Configuration
Set properties in `src/main/resources/application.properties` (or `application.yml`). Examples:
```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:tasks;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true

# JWT (example; adjust to your implementation)
app.jwt.secret=change-me
app.jwt.access-token-ttl=60s      # 1 minutes
app.jwt.refresh-token-ttl=7d       # 7 days
```
For MySQL/PostgreSQL, replace the datasource URL, username, password, and driver if needed.

## Authentication (JWT access & refresh tokens)
This API uses JSON Web Tokens (JWT) for stateless authentication.

Typical flow:
1. Login with credentials to receive an access token and a refresh token
2. Use the access token in the `Authorization` header for protected endpoints
3. When the access token expires, call the refresh endpoint with the refresh token to get a new access token

Example endpoints (adjust names/paths as in your code):
- `POST /api/auth/login` — returns `{ accessToken, refreshToken }`
- `POST /api/auth/refresh` — accepts refresh token and returns new `{ accessToken }`
- `POST /api/system/logout` — to invalidate refresh token server-side

Example headers for protected endpoints:
```
Authorization: Bearer <accessToken>
```

Example login request:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user@example.com","password":"password"}'
```

Example refresh request:
```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"<refreshToken>"}'
```

## API Overview
Base URL: `http://localhost:8080/api`

Example endpoints (adjust to your implementation):
- `GET /api/tasks` — list tasks
- `POST /api/tasks` — create a task with "Open" Status
- `PUT /api/tasks/{id}` — Status update to be "Done"
- `DELETE /api/tasks/{id}` — delete a task

Add the `Authorization: Bearer <accessToken>` header when authentication is required.

### Example Task JSON
```json
{
  "title": "Write README",
  "description": "Prepare project documentation"
}
```

## Using the Postman Collection
If the `postman/` folder is present, import the collection into Postman and set the `baseUrl` variable to `http://localhost:8080`. Run requests directly. If auth is enabled, set a collection variable `token` to the access token and configure the `Authorization` header as `Bearer {{token}}`.

## Running Tests
```bash
mvn test
```

## Common Issues
- Port already in use: change `server.port` in application properties
- Java version mismatch: ensure JDK 17+ is active
- make sure the lombok is working from Annotation setting
- Database connection failures: verify `spring.datasource.*` settings
- 401 Unauthorized: ensure `Authorization: Bearer <accessToken>` header is present and token is valid




