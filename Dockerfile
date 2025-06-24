FROM openjdk:23-ea-17-jdk
WORKDIR /app
COPY ./target/delivery-microservice-0.0.1-SNAPSHOT.jar .
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "delivery-microservice-0.0.1-SNAPSHOT.jar"]
