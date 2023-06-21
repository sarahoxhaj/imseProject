# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the entire project to the container
COPY . .

# Build the application
RUN mvn package -DskipTests

# Use a lightweight OpenJDK image as the base image
FROM openjdk:17-jdk-slim AS production

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the production stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your application is listening on
EXPOSE 8080
EXPOSE 3306
EXPOSE 9999

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
