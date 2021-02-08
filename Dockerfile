FROM openjdk:8-jdk-alpine

ADD target/dashboard-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar" ]
