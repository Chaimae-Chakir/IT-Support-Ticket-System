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
../captures/Capture1.png

## Testing(JUnit && Mockito)
Run backend tests:
   ```bash
   cd backend
   mvn test
   ```

# 1. Building & Running Without Docker

You can run everything locally (for development) using Maven and a local Oracle database instance.

1. **Set up the Oracle Database**
    - Ensure you have an Oracle DB running (e.g. Oracle XE).
    - Create a user (e.g. `c##chakir`) with a password (e.g. `chakir2001`).
    - (Optional) Run the included SQL script (`ticket_management_schema.sql`) to set up sample data. For example:

      ```sql
      sqlplus sys/chakir2001@localhost:1521/XE as sysdba
      ALTER SESSION SET CONTAINER=XE;
   -- CREATE USER c##chakir IDENTIFIED BY chakir2001;
   -- GRANT CONNECT, RESOURCE TO c##chakir;
   -- ALTER USER c##chakir DEFAULT TABLESPACE users;
   -- ALTER USER c##chakir QUOTA UNLIMITED ON users;
 
      -- Then:
      sqlplus c##chakir/chakir2001@localhost:1521/xe
      @ticket_management_schema.sql
      ```

2. **Configure Spring Boot**  
   Update `backend/src/main/resources/application.yaml` with your Oracle DB info

3. **Build and Run**

- In a terminal, go to `it-support-ticket-system/backend` and run:

```bash
mvn clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

- The backend should start on [http://localhost:8080](http://localhost:8080).


4. **Build and Run Run the Swing Client**

- Go to `it-support-ticket-system/frontend` and run:

```bash
mvn clean package
```

- This will produce, for example, `target/frontend-1.0-SNAPSHOT-jar-with-dependencies.jar`.

- Launch the client:

```bash
java -jar target/frontend-1.0-SNAPSHOT-jar-with-dependencies.jar
```

5. **Build and Run Login**

- Use test users you inserted into the DB. For example:

    - `employee1 / 1234` (Role: EMPLOYEE)
    - `it_support1 / 1234` (Role: IT_SUPPORT)


---
# 2. Building & Running With Docker

#### Docker Container for Backend and Oracle DB
Here’s how to deploy the backend and Oracle DB using Docker, ensuring all necessary instructions are included.

1. **Update `docker-compose.yml`** (place this in the project root):
   ```yaml
   version: '3.8'
   services:
     oracle-db:
       image: gvenzl/oracle-xe:21-slim
       container_name: oracle-db
       environment:
         - ORACLE_PASSWORD=chakir2001
         - APP_USER=c##chakir
         - APP_USER_PASSWORD=chakir2001
       ports:
         - "1521:1521"
       volumes:
         - ./backend/src/main/resources/ticket_management_schema.sql:/docker-entrypoint-initdb.d/ticket_management_schema.sql

     backend:
       build:
         context: ./backend
         dockerfile: Dockerfile
       container_name: it-support-backend
       depends_on:
         - oracle-db
       ports:
         - "8080:8080"
       environment:
         - SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle-db:1521/XEPDB1
         - SPRING_DATASOURCE_USERNAME=c##chakir
         - SPRING_DATASOURCE_PASSWORD=chakir2001
   ```

- The volume mounts `ticket_management_schema.sql` to initialize the database automatically.`

2. **Update `Dockerfile`** (place this in `backend/`):

```Dockerfile
   FROM maven:3.8.6-openjdk-17 AS build
   WORKDIR /app
   COPY pom.xml .
   COPY src ./src
   RUN mvn clean package -DskipTests

   FROM openjdk:17-jdk-slim
   WORKDIR /app
   COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "app.jar"]
```

3. **Deploy with Docker**

1. Navigate to the project root:

```bash
cd it-support-ticket-system
```

2. Build and start the containers:

```bash
docker-compose up --build
```

## Access

- **Backend:** [http://localhost:8080](http://localhost:8080)
- **Oracle DB:** `localhost:1521` (service name: `XEPDB1`)

4. **Troubleshooting**

- Ensure Docker is running.
- Check container logs if the backend fails to connect to the DB:

```bash
docker logs it-support-backend
docker logs oracle-db
```

- The Oracle container may take a minute to initialize fully—wait until the backend starts successfully.

---

# Swing Client as an Executable JAR

## Steps

### 1. Build the JAR

1. Navigate to `frontend/`:

```bash
cd frontend
```

2. Build with Maven:

```bash
mvn clean package
```

This generates `target/frontend-0.0.1-SNAPSHOT-jar-with-dependencies.jar`.

### 2. Run the JAR

```bash
java -jar target/frontend-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

- Ensure the backend is running (either via Docker or locally) at [http://localhost:8080](http://localhost:8080).

### 3. Notes

- The `pom.xml` in `frontend/` uses the `maven-assembly-plugin` to include all dependencies, making the JAR fully executable.
- Log in with credentials from `ticket_management_schema.sql` (e.g., `employee1 / 1234`).

---

# 3. Submission

## GitHub Repository Setup

### 1. Initialize Git Repository

1. Navigate to the project root:

```bash
cd it-support-ticket-system
```

2. Initialize Git:

```bash
git init
git add .
git commit -m "Initial commit of IT Support Ticket System"
```

### 2. Create GitHub Repository

1. Go to GitHub and create a new repository (e.g., `it-support-ticket-system`).
2. Copy the repository URL (e.g., `https://github.com/yourusername/it-support-ticket-system.git`).

### 3. Push to GitHub

```bash
git remote add origin <repository-url>
git branch -M main
git push -u origin main
```

### 4. Ensure Included Files

- `README.md`
- `API_DOCS.md`
- `docker-compose.yml` (in root)
- `backend/` (all source code, `pom.xml`, and resources)
- `frontend/` (all source code, `pom.xml`)
- `.gitignore` (example content below):

```text
target/
*.log
.idea/
*.iml
```

---

## Docker Setup for Local Execution

1. After cloning the repo, users can run:

```bash
git clone <repository-url>
cd it-support-ticket-system
docker-compose up --build
```

- This starts the backend and Oracle DB.
- The Swing client JAR must be built separately (see "Swing Client as Executable JAR" above).

### 6. Verification

1. Clone the repo to a new directory and test the setup:
    - Run `docker-compose up --build`.
    - Build and run the frontend JAR.
2. Ensure the `README.md` instructions work as expected.
