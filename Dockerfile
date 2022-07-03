FROM openjdk:11
LABEL mainteiner="Penkov Oleg"
ADD /target/Socks-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]