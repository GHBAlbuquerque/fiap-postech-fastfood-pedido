FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew build

COPY . .

EXPOSE 8080

CMD ["java", "-jar", "build/libs/fastfood-pedido-0.0.1-SNAPSHOT.jar"]