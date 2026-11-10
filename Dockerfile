# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q clean package -DskipTests

# Run stage (Tomcat 9 = javax.servlet)
FROM tomcat:9.0-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/sharath-devops-institute.war /usr/local/tomcat/webapps/ROOT.war
ENV DB_URL=jdbc:h2:file:/data/sharathdb;AUTO_SERVER=TRUE
VOLUME /data
EXPOSE 8080
