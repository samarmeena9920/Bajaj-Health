# Bajaj Health SQL Solver

This is a Spring Boot application that, on startup, performs the following:
- Sends a POST request to generate a webhook and receives a webhook URL and JWT access token.
- Determines which SQL question to solve based on the regNo (REG12347 → odd → Question 1).
- Solves the SQL problem and submits the final SQL query to the webhook using the JWT token.

## Requirements
- Java 17+
- Maven 3.6+

## Build
```
mvn clean package
```

## Run
```
java -jar target/bajaj-health-sql-solver-0.0.1-SNAPSHOT.jar
```

## Notes
- No controllers or endpoints are exposed.
- All logic runs on application startup.
