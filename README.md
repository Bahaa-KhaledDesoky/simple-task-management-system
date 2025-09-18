# Task Management System (Spring Boot)

A Spring Boot REST API for user authentication and task management. Users can register, log in, and manage their tasks (create, list, update status, delete). Security uses JWT access and refresh tokens.

## Table of Contents
- Features
- Tech Stack
- Project Structure
- Getting Started
- Configuration
- Authentication
- API Endpoints
- Error Handling
- Security Details
- Running Tests
- Notes
- API Endpoints (Postman & cURL Quick Reference)

## Features
- User registration and login (email + password)
- JWT-based authentication
  - Access token and refresh token support
- Task management for the authenticated user
  - Create task
  - List own tasks
  - Update task status
  - Delete task
- Layered architecture: Controller, Service, Repository, DTOs
- Validation with Jakarta Validation
- Global exception handling with `@ControllerAdvice`

## Tech Stack
- Java 17+
- Spring Boot (Web, Validation, Security)
- Spring Data JPA
- JWT (jjwt)
- Maven
- (DB configuration not shown here; H2/in-memory or external DB can be used)

## Project Structure
```
.
├── pom.xml
├── mvnw
├── mvnw.cmd
├── requirment.txt
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       ├── Config/
│   │   │       │   └── SpringSecurityConfig.java
│   │   │       ├── controllers/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── TaskController.java
│   │   │       ├── Dtos/
│   │   │       │   ├── LogInRequest.java
│   │   │       │   ├── Registration.java
│   │   │       │   ├── TaskDto.java
│   │   │       │   ├── TaskUpdate.java
│   │   │       │   └── TokenResponse.java
│   │   │       ├── Exceptions/
│   │   │       │   ├── GlobalHandleException.java
│   │   │       │   ├── InvalidTokenException.java
│   │   │       │   ├── TaskForbiddenException.java
│   │   │       │   ├── TaskNotFoundException.java
│   │   │       │   ├── UserExistException.java
│   │   │       │   ├── UserNotFoundException.java
│   │   │       │   └── WrongPasswordExceprion.java
│   │   │       ├── Mapping/
│   │   │       │   ├── TaskMapping.java
│   │   │       │   └── UserMapping.java
│   │   │       ├── model/
│   │   │       │   ├── AppUser.java
│   │   │       │   └── Task.java
│   │   │       ├── Repository/
│   │   │       │   ├── TaskRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── security/
│   │   │       │   ├── AuthEntryPointJwt.java
│   │   │       │   ├── AuthTokenFilter.java
│   │   │       │   ├── CustomUserDetails.java
│   │   │       │   ├── CustomUserDetailsService.java
│   │   │       │   └── JwtUtils.java
│   │   │       └── Services/
│   │   │           ├── AuthService.java
│   │   │           ├── TaskService.java
│   │   │           ├── TaskServiceImp.java
│   │   │           ├── UserService.java
│   │   │           └── UserServiceImp.java
│   │   └── resources/
│   │       └── application.properties (configure JWT & datasource)
└── README.md
```

## Getting Started

### Prerequisites
- Java JDK 17+
- Maven 3.8+

Check versions:
```bash
java -version
mvn -v
```

### Run (Dev)
```bash
./mvnw spring-boot:run
```
Windows PowerShell:
```bash
mvnw spring-boot:run
```
Default base URL: `http://localhost:8080`

### Build a Jar
```bash
mvn clean package -DskipTests
java -jar target/*.jar
```

## Configuration
Set in `application.properties`:
```properties
# JWT secret must be Base64-encoded for HS256
spring.app.JwtSecret=BASE64_ENCODED_SECRET_HERE

# Token lifetimes
app.jwt.access-token-ttl=1m
app.jwt.refresh-token-ttl=7d

# Example H2 (in-memory) datasource
spring.datasource.url=jdbc:h2:mem:tasks;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```
Generate a Base64 secret:
- PowerShell: `python - << EOF\nimport os, base64; print(base64.b64encode(os.urandom(32)).decode())\nEOF`
- Bash: `openssl rand -base64 32`

## Authentication
Security config (`SpringSecurityConfig`) allows anonymous access to:
- `/auth/login`
- `/auth/register`
- `/auth/{refresh}` (issue a new access token from a refresh token)

All other endpoints require `Authorization: Bearer <accessToken>`.

### DTOs
- `Registration`:
  - `email` (required, email format)
  - `name` (required)
  - `password` (required, min 8, must contain digit, lower, upper, special)
- `LogInRequest`:
  - `email` (required, email format)
  - `password` (same constraints as above)
- `TokenResponse`:
  - `refreshToken`, `accessToken`
- `TaskDto` (create task):
  - `title` (required, min 3)
  - `description` (required, min 3)
  - `status` (required; typical values: PENDING, IN_PROGRESS, COMPLETED)
- `TaskUpdate` (update status):
  - `status` (required; typical values: PENDING, IN_PROGRESS, COMPLETED)

## API Endpoints
Base URL: `http://localhost:8080`

### Auth

#### POST /auth/register
Request:
```json
{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "Password@123"
}
```
Success 200:
```json
123
```
(returns newly created user id)

cURL:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","name":"John Doe","password":"Password@123"}'
```

#### POST /auth/login
Request:
```json
{
  "email": "user@example.com",
  "password": "Password@123"
}
```
Success 200 (`TokenResponse`):
```json
{
  "refreshToken": "<refresh-jwt>",
  "accessToken": "<access-jwt>"
}
```

cURL:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"Password@123"}'
```

#### GET /auth/{refresh}
- Path variable is the refresh token (URL-encode if needed)
Success 200:
```json
"<new-access-jwt>"
```

cURL:
```bash
curl -X GET "http://localhost:8080/auth/<refreshToken>"
```

#### POST /auth/logout
- Requires valid access token
Success 200:
```json
true
```

cURL:
```bash
curl -X POST http://localhost:8080/auth/logout \
  -H "Authorization: Bearer <accessToken>"
```

### Tasks

All task endpoints require `Authorization: Bearer <accessToken>`.

#### GET /task
Success 200:
```json
[
  {
    "id": 1,
    "title": "Write README",
    "description": "Prepare project documentation",
    "status": "PENDING"
  }
]
```

cURL:
```bash
curl -X GET http://localhost:8080/task \
  -H "Authorization: Bearer <accessToken>"
```

#### POST /task
Request (`TaskDto`):
```json
{
  "title": "Write README",
  "description": "Prepare project documentation",
  "status": "PENDING"
}
```
Success 200 (returns `HttpStatus.CREATED` as body):
```json
"CREATED"
```

cURL:
```bash
curl -X POST http://localhost:8080/task \
  -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Write README","description":"Prepare project documentation","status":"PENDING"}'
```

#### PUT /task/{taskid}
Request (`TaskUpdate`):
```json
{
  "status": "COMPLETED"
}
```
Success 200 (returns `HttpStatus.ACCEPTED` as body):
```json
"ACCEPTED"
```

cURL:
```bash
curl -X PUT http://localhost:8080/task/1 \
  -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" \
  -d '{"status":"COMPLETED"}'
```

#### DELETE /task/{id}
Success 200 (returns `HttpStatus.OK` as body):
```json
"OK"
```

cURL:
```bash
curl -X DELETE http://localhost:8080/task/1 \
  -H "Authorization: Bearer <accessToken>"
```

## Error Handling
Global handler: `GlobalHandleException`.

Response shapes:
- Validation errors (400 Bad Request): field → message map
```json
{
  "title": "must not be blank",
  "description": "must not be blank",
  "status": "must not be blank"
}
```
- Business/auth errors: object with `HttpStatus` and `Message`
```json
{
  "HttpStatus": "400",
  "Message": "User already exists"
}
```

Mappings:
- 400: `MethodArgumentNotValidException`, `UserExistException`, `UserNotFoundException`, `WrongPasswordExceprion`, `TaskNotFoundException`, `RuntimeException`
- 403: `TaskForbiddenException`

## Security Details
- `AuthTokenFilter` extracts `Authorization: Bearer <token>` and validates JWT via `JwtUtils`
- `JwtUtils` expects Base64 `spring.app.JwtSecret`, uses HS256, sets `type` claim to `access` or `refresh`
- Access/refresh token TTLs from `app.jwt.access-token-ttl` and `app.jwt.refresh-token-ttl`

## Postman Setup
- Create a collection/environment variable `baseUrl` = `http://localhost:8080`
- After login, set `accessToken` to the returned token and add a default header:
  - `Authorization: Bearer {{accessToken}}`
- For refresh, pass the refresh token directly in the URL to `GET {{baseUrl}}/auth/{{refreshToken}}`

### Postman Collection
A ready-to-use Postman collection is included in the repo:
- `postman/Task Management API.postman_collection.json`

Import it into Postman, set environment variable `baseUrl` to `http://localhost:8080`, and (after login) set `accessToken` to your JWT to run the secured requests.

## Running Tests
```bash
mvn test
```

## Notes
- Refresh token is passed as a path variable to `/auth/{refresh}` per current implementation. Consider switching to `POST /auth/refresh` with a JSON body for better ergonomics.
- Task endpoints return Spring `HttpStatus` name strings in the body for create/update/delete; you may later prefer standard 201/204 codes with resource bodies.

## API Endpoints (Postman & cURL Quick Reference)

### Auth

POST /auth/register
- Request (Postman body):
```json
{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "Password@123"
}
```
- Response 200:
```json
123
```
- cURL:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","name":"John Doe","password":"Password@123"}'
```

POST /auth/login
- Request:
```json
{
  "email": "user@example.com",
  "password": "Password@123"
}
```
- Response 200:
```json
{
  "refreshToken": "<refresh-jwt>",
  "accessToken": "<access-jwt>"
}
```
- cURL:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"Password@123"}'
```

GET /auth/{refresh}
- Response 200:
```json
"<new-access-jwt>"
```
- cURL:
```bash
curl -X GET "http://localhost:8080/auth/<refreshToken>"
```

POST /auth/logout
- Headers: Authorization: Bearer <accessToken>
- Response 200:
```json
true
```
- cURL:
```bash
curl -X POST http://localhost:8080/auth/logout \
  -H "Authorization: Bearer <accessToken>"
```

### Tasks

GET /task
- Headers: Authorization: Bearer <accessToken>
- Response 200:
```json
[
  {
    "id": 1,
    "title": "Write README",
    "description": "Prepare project documentation",
    "status": "PENDING"
  }
]
```
- cURL:
```bash
curl -X GET http://localhost:8080/task \
  -H "Authorization: Bearer <accessToken>"
```

POST /task
- Headers: Authorization: Bearer <accessToken>
- Request:
```json
{
  "title": "Write README",
  "description": "Prepare project documentation",
  "status": "PENDING"
}
```
- Response 200:
```json
"CREATED"
```
- cURL:
```bash
curl -X POST http://localhost:8080/task \
  -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Write README","description":"Prepare project documentation","status":"PENDING"}'
```

PUT /task/{taskid}
- Headers: Authorization: Bearer <accessToken>
- Request:
```json
{
  "status": "COMPLETED"
}
```
- Response 200:
```json
"ACCEPTED"
```
- cURL:
```bash
curl -X PUT http://localhost:8080/task/1 \
  -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" \
  -d '{"status":"COMPLETED"}'
```

DELETE /task/{id}
- Headers: Authorization: Bearer <accessToken>
- Response 200:
```json
"OK"
```
- cURL:
```bash
curl -X DELETE http://localhost:8080/task/1 \
  -H "Authorization: Bearer <accessToken>"
```




