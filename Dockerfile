FROM openjdk:17-jdk

WORKDIR /app

COPY target/testeattus-0.0.1-SNAPSHOT.jar /app/testeattus-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "testeattus-0.0.1-SNAPSHOT.jar"]