FROM openjdk:15-jdk-alpine

ADD /backend /
RUN ["/gradlew", "build", "-x", "test"]
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# ARG DEPENDENCY=/backend/build/dependency
# COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY ${DEPENDENCY}/META-INF /app/META-INF
# COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/build/libs/demo-0.0.1-SNAPSHOT.jar"]
