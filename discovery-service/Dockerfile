FROM openjdk:8
MAINTAINER Piotr Minkowski <piotr.minkowski@play.pl>
VOLUME /tmp
ADD target/discovery-service-1.0-SNAPSHOT.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "/discovery-service.jar"]
EXPOSE 8761