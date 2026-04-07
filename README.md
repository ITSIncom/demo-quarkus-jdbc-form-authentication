# Quarkus Form Authentication Demo

A demo Quarkus application showcasing **form-based authentication** with a PostgreSQL-backed JDBC security realm.

## Features

- Form-based login/logout with cookie session management
- User registration with BCrypt password hashing
- Elytron JDBC security realm with role-based authorization
- Qute-templated HTML pages (login, register, dashboard)
- Automatic schema creation and seed data on startup
- PostgreSQL via Quarkus Dev Services (zero config in dev mode)

## Tech Stack

- **Quarkus 3.34** (REST, Qute, Arc)
- **Elytron Security JDBC** for authentication
- **FluentJDBC** for database access
- **PostgreSQL**
- **Java 21**

## Project Structure

```
src/main/java/com/incom/auth/
  web/                   # JAX-RS resources (Login, Registration, Logout, Dashboard)
  service/               # Business logic (UserService, SeedData)
  persistence/
    model/               # User entity
    repository/           # UserRepository (FluentJDBC)

src/main/resources/
  templates/             # Qute HTML templates
  application.properties # Security & datasource config
```

## Running in Dev Mode

```bash
./mvnw quarkus:dev
```

Quarkus Dev Services will automatically start a PostgreSQL container. The app seeds two default users on startup:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin    | admin |
| user     | user     | user  |

Open http://localhost:8080/login to get started.

## Endpoints

| Path              | Access         | Description            |
|-------------------|----------------|------------------------|
| `/login`          | Public         | Login form             |
| `/register`       | Public         | Registration form      |
| `/app/dashboard`  | Authenticated  | Protected dashboard    |
| `/j_security_check` | Public      | Form login action (POST) |

## Building for Production

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

Configure the datasource via environment variables or `application.properties` for production use.
