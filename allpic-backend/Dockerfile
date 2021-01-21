#
# Build stage
FROM maven:3-openjdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests

#
# Package stage
FROM openjdk:11-jre
ENV version="0.0.1-SNAPSHOT"
COPY --from=build "/app/target/allpic-$version.jar" "/opt/allpic/"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom/","-jar","/opt/allpic/allpic-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080