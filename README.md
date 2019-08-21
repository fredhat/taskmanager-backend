# TaskManager Spring Back-End Application

This project was built with Spring Boot Framework version 2.1.7.

## Build

Run `mvn package` to build the project. The build artifacts will be stored in the `target/` directory.

## Development server

Run `java -jar target/taskmanager-0.0.1.jar` for a dev server. Endpoint requests can be made to `http://localhost:8080/`.

## Swagger

While running the project server you can visit http://localhost:8080/swagger-ui.html to inspect the application's available endpoints.

## Docker

I have included the necessary Docker files for building a Docker image of this application. The following commands should help build and run that image:

```
docker build -t taskmanager-backend:dev .

docker run -d -p 8080:8080 --rm taskmanager-frontend:dev

Navigate to `http://localhost:8080/
```

This process is not guaranteed to work as I have not yet had a chance to test the docker build proces due to currently developing on a Windows machine.

I will confirm Docker functionality as soon as I have a chance to spin up a remote Linux server and recreate my dev environment on it.