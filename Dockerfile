FROM openjdk:15-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=backend/build/libs/*.jar
COPY ${JAR_FILE} app.jar
# ARG DEPENDENCY=/backend/build/dependency
# COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY ${DEPENDENCY}/META-INF /app/META-INF
# COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-Dserver.port=${PORT}",  "-jar","/app.jar"]
