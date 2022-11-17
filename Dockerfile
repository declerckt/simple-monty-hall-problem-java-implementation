FROM openjdk:19-jdk-alpine3.15

WORKDIR /app
COPY src/MhpImpl.java /app

RUN javac MhpImpl.java
ENTRYPOINT [ "java", "MhpImpl" ]