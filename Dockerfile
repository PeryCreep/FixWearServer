FROM openjdk
WORKDIR /FixWearServer
COPY /target/scala-2.13/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]