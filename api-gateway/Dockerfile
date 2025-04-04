# Stage 1: Build the JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/apigateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
