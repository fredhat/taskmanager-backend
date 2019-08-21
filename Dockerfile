# This is a standard dockerfile for containerizing the application.
FROM java:8
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/taskmanager-0.0.1.jar
ADD ${JAR_FILE} taskmanager.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/taskmanager.jar"]