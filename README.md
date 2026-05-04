# GameDatabase

A simple Spring Boot 4 REST API for managing game metadata using Spring Data JPA and an H2 database.

## Project Summary

This project exposes CRUD endpoints under `/api/games` for `Game` entities with fields:
- `id`
- `title`
- `genre`
- `platform`
- `releaseYear`

It uses Spring Boot 4.0.6, Java 21, and H2 as the runtime database.

## Features

- List all games
- Retrieve a game by ID
- Create a new game
- Update an existing game with partial updates
- Delete a game by ID
- Repository-based persistence using Spring Data JPA

## Prerequisites

- Java 21 SDK
- Git (optional)
- Gradle wrapper included in repository

## Build

From the project root:

```bash
./gradlew.bat clean build
```

## Run

Start the application with:

```bash
./gradlew.bat bootRun
```

The service runs by default on `http://localhost:8080`.

## API Endpoints

Base path: `/api/games`

### Get all games

```http
GET /api/games
```

### Get a game by ID

```http
GET /api/games/{id}
```

### Create a new game

```http
POST /api/games
Content-Type: application/json

{
  "title": "My Game",
  "genre": "Action",
  "platform": "PC",
  "releaseYear": "2026"
}
```

### Update an existing game

```http
PUT /api/games/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "platform": "Console"
}
```

Partial updates are supported: only non-null fields are applied.

### Delete a game

```http
DELETE /api/games/{id}
```

## Database Configuration

The application uses H2 with the datasource configured in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:file:~/games.db
    driver-class-name: org.h2.Driver
```

The schema is recreated on each startup because `spring.jpa.hibernate.ddl-auto` is configured as `create-drop`.

## Tests

Run unit tests with:

```bash
./gradlew.bat test
```

## Project Structure

- `src/main/java` - application source code
- `src/main/resources` - application configuration and static resources
- `src/test/java` - test code
- `build.gradle` - Gradle build and dependencies

## Notes

- Uses Lombok for entity boilerplate reduction.
- The repository interface is `com.suntheory.GameDatabase.repositories.GameRepository`.
- The REST controller is `com.suntheory.GameDatabase.controller.GameController`.

## Author

Nate Bloom | LinkedIn:  www.linkedin.com/in/njbloom
