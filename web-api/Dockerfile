# Build stage
FROM maven:3.6.3-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Package stage
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/web-api-0.0.1-SNAPSHOT.jar /usr/local/lib/web-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/web-api.jar", \
             "--spring.datasource.username=${DATABASE_USERNAME}", \
             "--spring.datasource.password=${DATABASE_PASSWORD}", \
             "--spring.datasource.url=jdbc:db2://database:${DATABASE_PORT}/${DATABASE_NAME}", \
             "--cors.allowedOrigins=${CORS_ALLOWED_ORIGINS}", \
             "--cors.allowedMethods=${CORS_ALLOWED_METHODS}"]