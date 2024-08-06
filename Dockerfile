FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/your-app.jar /app/your-app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
