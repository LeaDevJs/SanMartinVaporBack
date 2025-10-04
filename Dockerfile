# Imagen base con Java 17 (Render usa Java 17 por defecto)
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos del proyecto
COPY . .

# Damos permiso de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construimos el proyecto sin correr los tests
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080
EXPOSE 8080

# Comando para iniciar el backend
CMD ["java", "-jar", "target/*.jar"]
