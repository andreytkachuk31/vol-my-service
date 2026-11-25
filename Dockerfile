FROM adoptopenjdk/openjdk11:alpine
ADD target/vol-my-service-*.jar vol-my-service.jar
ENTRYPOINT  ["java", "-jar", "vol-my-service.jar"]
EXPOSE 8080
