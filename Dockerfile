FROM gradle:8.5-jdk21 AS build

WORKDIR /app
COPY . .

# 🔒 Dùng wrapper để build an toàn
RUN chmod +x ./gradlew && ./gradlew build --no-daemon

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/build/libs/FoodNFitBE-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
