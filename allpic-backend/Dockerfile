FROM openjdk:11-jre
ENV version="0.0.1-SNAPSHOT"
COPY "./target/allpic-$version.jar" "/opt/allpic/"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom/","-jar","/opt/allpic/allpic-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080