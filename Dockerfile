FROM eclipse-temurin:8
WORKDIR /app
COPY target/customer-service.jar customer-service.jar
EXPOSE 8083
CMD ["java", "-jar", "customer-service.jar"]