# Use OpenJDK as base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy your JAR into the container
COPY qalb-service.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
