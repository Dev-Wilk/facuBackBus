# Estágio de build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
# Isso permite melhor uso do cache do Docker
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copia o código fonte e compila o projeto
COPY src ./src
RUN mvn package -DskipTests

# Estágio de execução (imagem mais leve)
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia apenas o arquivo JAR gerado do estágio de build
COPY --from=build /app/target/facuBackBus-1.0-SNAPSHOT.jar ./facuBackBus.jar

# Expõe a porta do servidor Spring Boot (8082)
# Utilizamos a porta 8082 conforme configurado no application.properties
EXPOSE 8082

# Comando para executar a aplicação
CMD ["java", "-jar", "facuBackBus.jar"]
