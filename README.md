# Système de Gestion de Tickets d'Assistance IT

Ce projet est une application de gestion de tickets d'assistance IT. Elle comprend :

- **Backend** : Une application Spring Boot (Java 17) exposant une API REST avec Swagger/OpenAPI.
- **Base de données** : Oracle DB stockant les tickets, utilisateurs, commentaires et journaux d'audit.
- **Frontend** : Un client desktop Java Swing qui consomme l'API REST.

## Fonctionnalités

- **Création de tickets** : Les employés créent des tickets avec Titre, Description, Priorité (Basse, Moyenne, Haute), Catégorie (Réseau, Matériel, Logiciel, Autre), et Date de création automatique.
- **Suivi de statut** : Les tickets évoluent entre les états `NEW`, `IN_PROGRESS`, et `RESOLVED` (mis à jour uniquement par l'Assistance IT).
- **Rôles utilisateurs** :
   - **EMPLOYEE** : Créer et consulter ses propres tickets.
   - **IT_SUPPORT** : Voir tous les tickets, mettre à jour les statuts, et ajouter des commentaires.
- **Journal d'audit** : Suit les changements de statut et les commentaires.
- **Recherche & Filtrage** : Recherche de tickets par ID et statut.

## Stack technologique
- **Backend** : Java 17, Spring Boot 3, API RESTful avec Swagger/OpenAPI
- **Base de données** : Oracle SQL (testé avec Oracle XE 21c)
- **Frontend** : Java Swing (MigLayout)
- **Tests** : JUnit, Mockito
- **Déploiement** : Docker (backend + Oracle DB), JAR exécutable (client Swing)

## Prérequis
- **Java 17** : Pour construire et exécuter localement.
- **Maven 3.8+** : Pour la gestion des dépendances et les builds.
- **Docker & Docker Compose** : Pour le déploiement conteneurisé.
- **Git** : Pour le contrôle de version et la soumission.

## Structure du projet
[Structure du projet ici]

## Installation et exécution

### Méthode 1 : Exécution sans Docker

Vous pouvez tout exécuter localement (pour le développement) en utilisant Maven et une instance locale de base de données Oracle.

1. **Configuration de la base de données Oracle**
   - Assurez-vous d'avoir une base de données Oracle en cours d'exécution (ex: Oracle XE).
   - Créez un utilisateur (ex: `c##chakir`) avec un mot de passe (ex: `chakir2001`).
   - (Optionnel) Exécutez le script SQL inclus (`ticket_management_schema.sql`) pour configurer des données d'exemple :

     ```sql
     sqlplus sys/chakir2001@localhost:1521/XE as sysdba
     ALTER SESSION SET CONTAINER=XE;
     -- CREATE USER c##chakir IDENTIFIED BY chakir2001;
     -- GRANT CONNECT, RESOURCE TO c##chakir;
     -- ALTER USER c##chakir DEFAULT TABLESPACE users;
     -- ALTER USER c##chakir QUOTA UNLIMITED ON users;

     -- Puis:
     sqlplus c##chakir/chakir2001@localhost:1521/xe
     @ticket_management_schema.sql
     ```

2. **Configuration de Spring Boot**  
   Mettez à jour `backend/src/main/resources/application.yaml` avec vos informations de base de données Oracle

3. **Construction et exécution du backend**

   ```bash
   cd it-support-ticket-system/backend
   mvn clean package
   java -jar target/backend-0.0.1-SNAPSHOT.jar
   ```

   - Le backend devrait démarrer sur [http://localhost:8080](http://localhost:8080).

4. **Construction et exécution du client Swing**

   ```bash
   cd it-support-ticket-system/frontend
   mvn clean package
   java -jar target/frontend-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

5. **Connexion**
   - Utilisez les utilisateurs de test insérés dans la base de données. Par exemple :
      - `employee1 / 1234` (Rôle : EMPLOYEE)
      - `it_support1 / 1234` (Rôle : IT_SUPPORT)

### Méthode 2 : Exécution avec Docker

1. **Mettez à jour `docker-compose.yml`** (placez-le à la racine du projet) :
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

2. **Créez un `Dockerfile`** (placez-le dans `backend/`) :

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

3. **Déployez avec Docker**

   ```bash
   cd it-support-ticket-system
   docker-compose up --build
   ```

4. **Accès**
   - **Backend :** [http://localhost:8080](http://localhost:8080)
   - **Oracle DB :** `localhost:1521` (nom du service : `XEPDB1`)

5. **Dépannage**
   - Assurez-vous que Docker est en cours d'exécution.
   - Vérifiez les logs des conteneurs si le backend ne parvient pas à se connecter à la base de données :
     ```bash
     docker logs it-support-backend
     docker logs oracle-db
     ```
   - Le conteneur Oracle peut prendre une minute pour s'initialiser complètement.

## Client Swing en tant que JAR exécutable

1. **Construire le JAR**
   ```bash
   cd frontend
   mvn clean package
   ```
   Cela génère `target/frontend-0.0.1-SNAPSHOT-jar-with-dependencies.jar`.

2. **Exécuter le JAR**
   ```bash
   java -jar target/frontend-0.0.1-SNAPSHOT-jar-with-dependencies.jar
   ```
   - Assurez-vous que le backend est en cours d'exécution à [http://localhost:8080](http://localhost:8080).

## Soumission via GitHub

1. **Initialiser le dépôt Git**
   ```bash
   cd it-support-ticket-system
   git init
   git add .
   git commit -m "Commit initial du système de tickets d'assistance IT"
   ```

2. **Créer un dépôt GitHub**
   - Créez un nouveau dépôt sur GitHub (ex: `it-support-ticket-system`).
   - Copiez l'URL du dépôt.

3. **Pousser vers GitHub**
   ```bash
   git remote add origin <URL-du-dépôt>
   git branch -M main
   git push -u origin main
   ```

4. **Fichiers à inclure**
   - `README.md`
   - `API_DOCS.md`
   - `docker-compose.yml` (à la racine)
   - `backend/` (tout le code source, `pom.xml`, et les ressources)
   - `frontend/` (tout le code source, `pom.xml`)
   - `.gitignore` :
     ```
     target/
     *.log
     .idea/
     *.iml
     ```

## Tests

Pour exécuter les tests backend :
```bash
cd backend
mvn test
```