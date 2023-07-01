## Use an official Maven image as the base image
#FROM maven:3.8.4-openjdk-17-slim AS build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline -B
#COPY . .
#RUN mvn package -DskipTests
#FROM openjdk:17-jdk-slim AS production
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
#

# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn package -DskipTests

# Build stage for MySQL container
FROM mysql:latest AS mysql
ENV MYSQL_ROOT_PASSWORD=projektreni2021
ENV MYSQL_DATABASE=library
COPY --from=build /app/target/*.jar app.jar
RUN apt-get update && apt-get install -y netcat
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
CMD ["/wait-for.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]

# Build stage for MongoDB container
FROM openjdk:17-jdk-slim AS mongodb
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 9999
EXPOSE 27017
EXPOSE 3306
EXPOSE 8081

