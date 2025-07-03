# Estágio de build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Estágio final
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/facuBackBus-1.0-SNAPSHOT.jar ./facuBackBus.jar

EXPOSE 8082

CMD ["java", "-jar", "facuBackBus.jar"]
