FROM openjdk:18-ea-11-alpine3.15
VOLUME /temp
EXPOSE 8080
ADD Api-TDD/target/Api-TDD-0.0.1-SNAPSHOT.jar Api-TDD.jar
ENTRYPOINT ["java","-jar","/Api-TDD.jar"]
