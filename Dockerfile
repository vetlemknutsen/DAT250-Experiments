FROM gradle:8-jdk22 AS builder

WORKDIR /home/gradle

COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

RUN gradle bootJar --no-daemon


FROM eclipse-temurin:22-alpine

RUN addgroup -S appgroup && adduser -S testuser -G appgroup

COPY --from=builder /home/gradle/build/libs/*.jar app.jar

RUN chown testuser:appgroup app.jar

USER testuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
