FROM eclipse-temurin:8
WORKDIR /app
COPY target/customer-service-0.0.1-SNAPSHOT.jar customer-service-0.0.1-SNAPSHOT.jar
EXPOSE 8083
CMD ["java", "-jar", "customer-service-0.0.1-SNAPSHOT.jar"]