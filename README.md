# Gym System Backend API

A backend API for managing gym members, trainers, schedules, and exercises, built with Java Spring Boot and MySQL.

## Features
- Member and trainer registration/login
- Schedule management
- Exercise tracking
- Status updates for members
- CRUD operations for all entities

## Tech Stack
- Java Spring Boot
- MySQL / MariaDB
- Postman for API testing

## Example Endpoints
- POST /login → Authenticate member or trainer
- GET /schedules/{memberId} → View schedule for a member
- PUT /schedules/{scheduleId}/status → Update schedule status
- GET /exercises → List all exercises
- POST /exercises → Add a new exercise
- PUT /exercises/{exerciseId} → Update exercise
- DELETE /exercises/{exerciseId} → Delete exercise

## How to Run
1. Clone the repo: `git clone https://github.com/davidsmaha/Gym-Management-System.git`
2. Open the project in IntelliJ IDEA
3. Configure your database connection in `application.properties` (host, username, password)
4. Run the application:
   - Using IntelliJ: Right-click on the main class → Run
   - Or using terminal: `mvn spring-boot:run`
5. Test the API endpoints using Postman or any API client
