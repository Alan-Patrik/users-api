FROM openjdk:11.0.12-slim-buster

COPY /target/user_api*.jar user_api.jar

SHELL ["/bin/sh", "-c"]

CMD java -jar user_api.jar