# Sharath DevOps Institute – Login & Courses App

Java (Servlet + JSP + JDBC) web app: login page → database-backed course list.

## Requirements
- JDK 17+
- Maven 3.8+

## Run (no DB install needed – uses embedded H2)
```bash
mvn jetty:run
```
Open http://localhost:8080

Demo logins: `student / student123` and `admin / admin123`

## Build WAR (deploy to Tomcat 9)
```bash
mvn clean package
# target/sharath-devops-institute.war  -> copy to Tomcat 9 webapps/ROOT.war
```

## Use MySQL instead of H2
Set environment variables before running:
```bash
export DB_URL="jdbc:mysql://localhost:3306/sharath_institute?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true"
export DB_USER=root
export DB_PASSWORD=yourpassword
mvn jetty:run
```
Tables and sample data are created automatically on first start (see `sql/mysql-schema.sql`).

## Docker
```bash
docker build -t sharath-devops-institute .
docker run -p 8080:8080 -v sharathdata:/data sharath-devops-institute
```

## Structure
- `servlet/` Login, Courses, Logout
- `filter/AuthFilter` protects /courses
- `dao/` JDBC queries (PreparedStatement)
- `listener/AppInitListener` creates tables + seeds users/courses
- `util/PasswordUtil` PBKDF2 password hashing
- `webapp/WEB-INF/views/` JSP pages

Change the demo passwords before any real deployment.
