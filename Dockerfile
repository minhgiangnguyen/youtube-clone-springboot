# Build Stage
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/youtubeclone-0.0.1-SNAPSHOT.jar /app/youtubeclone-0.0.1-SNAPSHOT.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "your-app.jar"]