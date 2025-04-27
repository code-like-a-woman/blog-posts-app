# -------- Build Stage --------
# Use Maven + Java 21 as the base image
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy pom.xml to download Maven dependencies
COPY pom.xml .

# Donwload Maven dependencies and cache them
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Compile the application
RUN mvn clean package -DskipTests

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Create a non-root user
RUN adduser -D -u 1000 appuser

# 2. Change user to appuser
USER appuser

COPY --from=build /app/target/blog-posts-app.jar blog-posts-app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "blog-posts-app.jar"]