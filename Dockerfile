# Dùng JDK 21 vì bạn đã cấu hình trong build.gradle.kts
FROM eclipse-temurin:21-jdk

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file jar từ máy vào container
COPY build/libs/FoodNFitBE-0.0.1-SNAPSHOT.jar app.jar

# Expose port cho Render
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
