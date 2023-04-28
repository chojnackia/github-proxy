FROM openjdk:17-jdk-slim-bullseye

WORKDIR /app
COPY target/github-proxy-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-XX:+PrintFlagsFinal", "-XX:MaxRAMPercentage=70", "-Djava.security.egd=file:/dev/./urandom", "-cp", ".:app.jar", "autorun.code.challenge.githubproxy.GithubProxyApplication"]