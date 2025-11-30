# Use a lightweight Java runtime (no Maven needed)
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file you just built on your machine
COPY target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Environment variables (default to host.docker.internal for local MySQL)
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/catlogagent?createDatabaseIfNotExist=true
ENV DB_USERNAME=root
ENV DB_PASSWORD=your_password

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]