FROM gradle:7.6-jdk17-jammy AS builder

WORKDIR /app
COPY . .

RUN ["chmod", "+x", "./gradlew"]
RUN ["./gradlew", "bootjar"]

FROM gradle:7.6-jdk17-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/chillvibe-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/chillvibe-0.0.1-SNAPSHOT.jar"]
