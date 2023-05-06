# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/
RUN mvn clean package

# Run stage
FROM openjdk:17-jdk-slim-bullseye AS run
WORKDIR /app
COPY --from=build /build/target/github-proxy-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
