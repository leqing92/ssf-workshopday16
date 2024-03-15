FROM maven:3-eclipse-temurin-21

WORKDIR /app

COPY mvnw.cmd .
COPY mvnw .
COPY pom.xml .
COPY src src
COPY .mvn .mvn

RUN mvn package -Dmaven.test.skip=true

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT java -jar target/day16-workshop-0.0.1-SNAPSHOT.jar
