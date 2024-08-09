#
# Build stage
#
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:21-jdk
COPY --from=build /target/youtubeclone-0.0.1-SNAPSHOT.jar youtubeclone.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","youtubeclone.jar"]

