# Usa una imagen base de Java 17
FROM openjdk:17-jdk-slim

# Añade un argumento para tu .jar
ARG JAR_FILE=build/libs/tu-aplicacion.jar

# Copia el .jar al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
