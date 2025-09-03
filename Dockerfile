FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/litflix-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 5005
ENTRYPOINT ["java", "-jar", "app.jar"]
