FROM eclipse-temurin:17-jdk-alpine
ADD target/show-us-*.jar show-us.jar
ENTRYPOINT ["java", "-jar", "/show-us.jar"]