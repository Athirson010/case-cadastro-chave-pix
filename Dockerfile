FROM maven:3.8-amazoncorretto-17
COPY . .
RUN mvn clean package

FROM openjdk:17-alpine
COPY --from=0 /target/*.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./cadastro-de-chaves-pix-service.jar"]