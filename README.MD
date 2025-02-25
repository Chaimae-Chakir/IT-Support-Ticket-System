# IT Support Ticket System

This project is a simple IT Support Ticket management application. It consists of:

- **Backend**: A Spring Boot application (Java 17) exposing a REST API with Swagger/OpenAPI.
- **Database**: Oracle DB storing tickets, users, comments, and audit logs.
- **Frontend**: A Java Swing desktop client that consumes the REST API.

## Features

- **Ticket Creation**: Employees create tickets with Title, Description, Priority (Low, Medium, High), Category (Network, Hardware, Software, Other), and automatic Creation Date.
- **Status Tracking**: Tickets transition between `NEW`, `IN_PROGRESS`, and `RESOLVED` (updated only by IT Support).
- **User Roles**:
- **EMPLOYEE**: Create and view own tickets.
- **IT_SUPPORT**: View all tickets, update statuses, and add comments.
- **Audit Log**: Tracks status changes and comments.
- **Search & Filter**: Search tickets by ID and status.

## Technology Stack
- **Backend**: Java 17, Spring Boot 3, RESTful API with Swagger/OpenAPI
- **Database**: Oracle SQL (tested with Oracle XE 21c)
- **Frontend**: Java Swing (MigLayout)
- **Testing**: JUnit, Mockito
- **Deployment**: Docker (backend + Oracle DB), executable JAR (Swing client)

## Prerequisites
- **Java 17**: For building and running locally.
- **Maven 3.8+**: For dependency management and builds.
- **Docker & Docker Compose**: For containerized deployment.
- **Git**: For version control and submission.
## Project Structure
<img src="captures/Capture1.PNG">
