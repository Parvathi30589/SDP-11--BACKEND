# SDP-11 Backend — Student Group Project Management Platform

> 24SDCS02E-S01-SDP-11 | Full Stack Application Development (FSAD)
> Spring Boot REST API powering the Student Group Project Management Platform, documented with Swagger and OpenAPI.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=flat-square&logo=jsonwebtokens)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=flat-square&logo=swagger)](https://swagger.io/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=flat-square&logo=apachemaven)](https://maven.apache.org/)

---

## Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Swagger Documentation](#swagger-documentation)
- [Security and Authentication](#security-and-authentication)
- [Environment Variables](#environment-variables)
- [Error Handling](#error-handling)
- [Git Branch Strategy](#git-branch-strategy)
- [Team](#team)

---

## Project Overview

This is the backend REST API for the Student Group Project Management Platform — a web-based system that enables efficient management of student group projects, including task assignments, progress tracking, collaboration, and performance monitoring.

Built with Spring Boot 3, secured with Spring Security + JWT, and fully documented via Swagger UI.

The API serves the React frontend at `http://localhost:3000` and is accessible at `http://localhost:8080`.

### User Roles Supported

| Role | Access Level |
|------|--------------|
| `TEACHER` | Create and manage projects, groups, tasks, review submissions, grade and provide feedback, view reports |
| `STUDENT` | View assigned projects, update task status, collaborate via chat, submit project files |
| `PARENT` | View linked student's projects, monitor progress, access feedback and grades |

---

## Architecture

```text
React Frontend (Port 3000)
        |
        | HTTPS / REST (JSON)
        v
Spring Boot Application (Port 8080)
        |
        |-- Controller Layer   -> REST endpoints and request/response mapping
        |-- Service Layer      -> Business logic and validations
        |-- Repository Layer   -> JPA and Hibernate data access
        `-- Security Layer     -> JWT auth filter and role-based access control
                |
                v
           MySQL Database (student_project_db)
```

### Design Patterns Used

- Layered Architecture: Controller -> Service -> Repository
- DTO Pattern: Separate Request and Response DTOs, entities never exposed directly
- Repository Pattern: Spring Data JPA repositories per entity
- Stateless JWT Authentication: No server-side session storage
- Global Exception Handling: Centralized error responses via @ControllerAdvice

---

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT (jjwt) |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8.x |
| API Docs | SpringDoc OpenAPI 3 (Swagger UI) |
| Build Tool | Maven |
| Validation | Spring Validation (@Valid, @NotBlank, etc.) |
| Logging | SLF4J / Logback |

---

## Project Structure

```text
SDP-11-BACKEND/
├── src/
│   └── main/
│       ├── java/com/studentproject/
│       │   ├── config/
│       │   │   ├── SecurityConfig.java          # Spring Security + CORS configuration
│       │   │   ├── SwaggerConfig.java           # OpenAPI bean + JWT Bearer auth scheme
│       │   │   └── JwtAuthFilter.java           # JWT validation filter (OncePerRequestFilter)
│       │   ├── controller/
│       │   │   ├── AuthController.java          # /api/auth/**
│       │   │   ├── UserController.java          # /api/users/**
│       │   │   ├── ProjectController.java       # /api/projects/**
│       │   │   ├── GroupController.java         # /api/groups/**
│       │   │   ├── TaskController.java          # /api/tasks/**
│       │   │   ├── MilestoneController.java     # /api/milestones/**
│       │   │   ├── SubmissionController.java    # /api/submissions/**
│       │   │   ├── ChatController.java          # /api/chat/**
│       │   │   ├── FeedbackController.java      # /api/feedback/**
│       │   │   ├── ParentController.java        # /api/parent/**
│       │   │   └── ReportController.java        # /api/reports/**
│       │   ├── dto/
│       │   │   ├── request/                     # LoginRequest, RegisterRequest, CreateProjectRequest, etc.
│       │   │   └── response/                    # AuthResponse, ProjectResponse, TaskResponse, etc.
│       │   ├── entity/
│       │   │   ├── User.java
│       │   │   ├── Project.java
│       │   │   ├── Group.java
│       │   │   ├── GroupMember.java
│       │   │   ├── Task.java
│       │   │   ├── Milestone.java
│       │   │   ├── Submission.java
│       │   │   ├── ChatMessage.java
│       │   │   ├── Feedback.java
│       │   │   └── ParentStudentLink.java
│       │   ├── exception/
│       │   │   ├── GlobalExceptionHandler.java  # @ControllerAdvice for all exceptions
│       │   │   ├── ResourceNotFoundException.java
│       │   │   └── UnauthorizedException.java
│       │   ├── repository/                      # JPA Repository interfaces per entity
│       │   ├── service/
│       │   │   ├── AuthService.java
│       │   │   ├── ProjectService.java
│       │   │   ├── GroupService.java
│       │   │   ├── TaskService.java
│       │   │   ├── MilestoneService.java
│       │   │   ├── SubmissionService.java
│       │   │   ├── ChatService.java
│       │   │   ├── FeedbackService.java
│       │   │   ├── ParentService.java
│       │   │   ├── ReportService.java
│       │   │   └── impl/                        # Service implementation classes
│       │   └── util/
│       │       └── JwtUtil.java                 # Token generation, validation, extraction
│       └── resources/
│           ├── application.properties
│           └── data.sql                         # Default admin seed data
├── pom.xml
└── README.md
```

---

## Database Schema

### Database Name: `student_project_db`

### Key Entities and Relationships

```text
users
  |-- (TEACHER) creates --> projects
  |                             |--< tasks (assignedTo: STUDENT)
  |                             |--< milestones
  |                             |--< submissions (submittedBy: STUDENT)
  |                             `--< feedback (student <-> givenBy: TEACHER)
  |
  |-- groups
  |     |--< group_members (student: users)
  |     `--< chat_messages (sender: users)
  |
  `-- parent_student_links (parent: users <-> student: users)
```

### Core Tables

| Table | Description |
|-------|-------------|
| `users` | All platform users with role (TEACHER, STUDENT, PARENT) |
| `projects` | Projects created by teachers |
| `groups` | Student groups linked to projects |
| `group_members` | Students assigned to groups |
| `tasks` | Tasks assigned to students within a project |
| `milestones` | Project milestones defined by teachers |
| `submissions` | Project file submissions by students |
| `chat_messages` | Group chat messages |
| `feedback` | Teacher feedback and grades per student per project |
| `parent_student_links` | Parent-to-student relationship mapping |

---

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.8+
- MySQL 8.x running locally
- Postman or Swagger UI for API testing

### Step 1 — Clone the Repository

```bash
git clone https://github.com/Parvathi30589/SDP-11-BACKEND.git
cd SDP-11-BACKEND
```

### Step 2 — Create MySQL Database

```sql
CREATE DATABASE student_project_db;
```

### Step 3 — Configure application.properties

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/student_project_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT
jwt.secret=${JWT_SECRET:sdp11_secret_key_2026}
jwt.expiration=${JWT_EXPIRATION_MS:86400000}

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# CORS
cors.allowed-origins=http://localhost:3000
```

### Step 4 — Run the Application

```bash
mvn spring-boot:run
```

| URL | Description |
|-----|-------------|
| `http://localhost:8080` | Base API URL |
| `http://localhost:8080/swagger-ui.html` | Swagger UI (API Documentation) |
| `http://localhost:8080/api-docs` | Raw OpenAPI JSON |

### Running Tests

```bash
mvn test
```

---

## API Endpoints

All endpoints are prefixed with `/api`. Protected endpoints require:

```text
Authorization: Bearer <token>
```

### Auth — `/api/auth`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/auth/register` | Register new user (TEACHER, STUDENT, PARENT) | Public |
| POST | `/api/auth/login` | Login and receive JWT token | Public |

### Users — `/api/users`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/users/me` | Get current logged-in user profile | Authenticated |
| PUT | `/api/users/me` | Update own profile | Authenticated |
| GET | `/api/users` | Get all users | TEACHER |

### Projects — `/api/projects`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/projects` | Create new project | TEACHER |
| GET | `/api/projects` | Get all projects | ALL |
| GET | `/api/projects/{id}` | Get project by ID | ALL |
| PUT | `/api/projects/{id}` | Update project details | TEACHER |
| DELETE | `/api/projects/{id}` | Delete project | TEACHER |
| GET | `/api/projects/my` | Get projects assigned to current student | STUDENT |

### Groups — `/api/groups`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/groups` | Create a new group | TEACHER |
| GET | `/api/groups` | Get all groups | ALL |
| GET | `/api/groups/{id}` | Get group by ID | ALL |
| POST | `/api/groups/{id}/members` | Add student to group | TEACHER |
| DELETE | `/api/groups/{id}/members/{studentId}` | Remove student from group | TEACHER |

### Tasks — `/api/tasks`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/tasks` | Create and assign task | TEACHER |
| GET | `/api/tasks/project/{projectId}` | Get all tasks for a project | ALL |
| GET | `/api/tasks/my` | Get tasks assigned to current student | STUDENT |
| PUT | `/api/tasks/{id}` | Update task details | TEACHER |
| PUT | `/api/tasks/{id}/status` | Update task status (PENDING / IN_PROGRESS / COMPLETED) | STUDENT |
| DELETE | `/api/tasks/{id}` | Delete task | TEACHER |

### Milestones — `/api/milestones`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/milestones` | Create milestone | TEACHER |
| GET | `/api/milestones/project/{projectId}` | Get milestones by project | ALL |
| PUT | `/api/milestones/{id}` | Update milestone | TEACHER |
| DELETE | `/api/milestones/{id}` | Delete milestone | TEACHER |

### Submissions — `/api/submissions`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/submissions` | Submit project file | STUDENT |
| GET | `/api/submissions/project/{projectId}` | View all submissions for a project | TEACHER |
| GET | `/api/submissions/my` | View own submissions | STUDENT |

### Chat — `/api/chat`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/chat` | Send a chat message | STUDENT |
| GET | `/api/chat/group/{groupId}` | Get full chat history for a group | ALL |

### Feedback — `/api/feedback`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/feedback` | Provide feedback and grade | TEACHER |
| GET | `/api/feedback/project/{projectId}` | Get feedback by project | ALL |
| GET | `/api/feedback/student/{studentId}` | Get all feedback for a student | TEACHER, PARENT |

### Parent — `/api/parent`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/parent/link` | Link a parent account to a student | PARENT |
| GET | `/api/parent/my-students` | Get list of linked students | PARENT |
| GET | `/api/parent/progress/{studentId}` | View student's full progress | PARENT |

### Reports — `/api/reports`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/reports/project/{projectId}` | Get project summary report | TEACHER |
| GET | `/api/reports/student/{studentId}` | Get student performance report | TEACHER |

---

## Swagger Documentation

Once the backend is running, open:

```text
http://localhost:8080/swagger-ui.html
```

Raw OpenAPI JSON:

```text
http://localhost:8080/api-docs
```

### How to Test Protected APIs in Swagger

1. Call `POST /api/auth/login` with valid credentials
2. Copy the JWT token from the response
3. Click the `Authorize` button at the top right of Swagger UI
4. Enter: `Bearer <your_token>`
5. All protected endpoints are now accessible

---

## Security and Authentication

### JWT Authentication Flow

```text
1. User sends credentials  ->  POST /api/auth/login
2. Server validates credentials against DB
3. Server generates signed JWT token
4. JWT returned in response: { token, role, name }
5. Client stores token in localStorage
6. All subsequent requests include:
       Authorization: Bearer <token>
7. JwtAuthFilter intercepts every request
8. Filter validates token signature and expiry
9. Spring Security sets SecurityContext with user role
10. Role-based access control applied per endpoint
```

### Default Seeded Admin Account

| Field | Value |
|-------|-------|
| Email | admin@school.com |
| Password | Admin@123 |
| Role | TEACHER |

### CORS Configuration

Allowed origins:

- `http://localhost:3000` (React frontend)

---

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_USERNAME` | MySQL username | `root` |
| `DB_PASSWORD` | MySQL password | `root` |
| `JWT_SECRET` | JWT signing secret key | `sdp11_secret_key_2026` |
| `JWT_EXPIRATION_MS` | Token expiry in milliseconds | `86400000` (24 hrs) |
| `CORS_ALLOWED_ORIGINS` | Allowed frontend origins | `http://localhost:3000` |

Do not commit sensitive credentials. Use environment variables or a `.env` file in production.

---

## Error Handling

All exceptions return a consistent JSON error response:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Project not found with id: 5",
  "timestamp": "2026-04-09T10:30:00"
}
```

### Handled Exceptions

| Exception | HTTP Status |
|-----------|-------------|
| `ResourceNotFoundException` | 404 Not Found |
| `UnauthorizedException` | 403 Forbidden |
| `MethodArgumentNotValidException` | 400 Bad Request |
| `Generic Exception` | 500 Internal Server Error |

---

## Git Branch Strategy

```text
main
├── member1/auth-core-backend      <- Parvathi: Auth, JWT, Security, Projects, Groups, Swagger
├── member2/features-backend       <- Arshiya: Tasks, Milestones, Submissions, Reports, Exception Handling
└── member3/integration-backend    <- Akshaya: Feedback, Chat, Parent APIs, DTO wiring, Frontend connect
```

Each member works on their own branch and raises a Pull Request to `main` after completion. This ensures individual contributions are clearly visible in Git history.

---

## Deployment

Build the project:

```bash
mvn clean install -DskipTests
```

Run the JAR:

```bash
java -jar target/student-project-backend-0.0.1-SNAPSHOT.jar
```

Can be deployed on platforms like Render, Railway, or AWS EC2.

---

## Team

SDP Group 11 — FSAD | 24SDCS02E-S01-SDP-11

| Member | Role | Contributions |
|--------|------|---------------|
| Parvathi | Team Lead / Full Stack Developer | Spring Boot setup, JWT Auth, Spring Security, User/Project/Group APIs, Swagger config, Git management |
| Arshiya | Backend Developer | Task, Milestone, Submission, Report APIs, GlobalExceptionHandler, input validations |
| Akshaya | Backend Developer | Feedback, Chat, Parent APIs, DTO wiring, Axios API integration with React frontend |

---

## License

Developed as part of the Full Stack Application Development (FSAD) course.
Academic use only — 2025-26.
