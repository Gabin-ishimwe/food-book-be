FROM openjdk as builder
COPY . .
RUN ./mvnw clean package

FROM openjdk
COPY --from=builder ./target/food-book-be-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djasypt.encryptor.password=appSecretKey", "-jar", "app.jar", "--server.port=8080", "--spring.profiles.active=prod", "--spring.config.location=classpath:/application-prod.yml"]

## java -Djasypt.encryptor.password=appSecretKey  -jar ./target/food-book-be-0.0.1-SNAPSHOT.jar --server.port=$PORT --spring.profiles.active=prod --spring.config.location=classpath:/application-prod.yml