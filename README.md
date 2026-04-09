# Student Project Backend

Spring Boot backend for Student Group Project Management Platform.

## Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Springdoc OpenAPI

## Run
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/student_project_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="<your_password>"

mvn spring-boot:run
```

App port: `5050`

Swagger:
- `http://localhost:5050/swagger-ui/index.html`
- `http://localhost:5050/v3/api-docs`

## Package Structure
- `config`
- `controller`
- `service`
- `repository`
- `entity`
- `dto`
- `exception`
- `util`
