FROM openjdk:17.0.2-slim-bullseye

COPY Bank_REST-main/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]