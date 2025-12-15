# -------- Build stage --------
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

# Copiamos pom y cacheamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Runtime stage --------
FROM amazoncorretto:21-alpine
WORKDIR /app

# Copiamos el jar
COPY --from=build /app/target/*.jar app.jar

# Cloud Run usa PORT
ENV PORT=8080
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
