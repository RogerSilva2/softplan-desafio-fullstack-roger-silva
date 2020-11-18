FROM openjdk:11-jre

RUN addgroup --gid 2000 --system spring && \
    adduser --uid 2000 --system spring --gid 2000

USER spring:spring

ARG JAR_FILE=target/*.jar
ARG PORT=8080

COPY ${JAR_FILE} app.jar

EXPOSE ${PORT}

HEALTHCHECK CMD curl --fail http://localhost:${PORT}/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app.jar"]