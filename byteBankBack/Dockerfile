# Utiliza a imagem base do OpenJDK que contém o Java Runtime Environment
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR construído do seu projeto para o diretório de trabalho do container
COPY ./bytebank/target/bytebank-0.0.1-SNAPSHOT.jar /app/bytebank.jar

# Expõe a porta que a aplicação usará
EXPOSE 8080

# Define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "bytebank.jar"]