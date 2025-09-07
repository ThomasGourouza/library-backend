# ====== BUILD ======
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn -q -B -DskipTests dependency:go-offline
COPY . .
RUN mvn -q -B -DskipTests package

# ====== RUNTIME ======
FROM eclipse-temurin:21-jre
ENV TZ=Europe/Paris \
    SPRING_PROFILES_ACTIVE=dev \
    JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"
# non-root user
RUN useradd -u 10001 -r -s /sbin/nologin appuser
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8081
USER appuser
ENTRYPOINT ["java","-jar","/app/app.jar"]
