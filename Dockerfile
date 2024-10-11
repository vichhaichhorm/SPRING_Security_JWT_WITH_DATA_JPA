# Use JRE 21 for the final runtime stage
FROM alpine/java:21-jdk

# Copy the executable JAR file from the build stage
COPY /build/libs/*.jar /app/app.jar

# Expose the port on which your Spring application will run
EXPOSE 8081

# Set the command to run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]

