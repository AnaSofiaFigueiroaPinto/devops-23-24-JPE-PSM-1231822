# Techincal Report Of Class Assignment 4 Part 2

## General Introduction
The goal for this first part of this class assignment is to display the usage of containers. This first part is composed of two versions, where the first version is to create a Dockerfile that provides all the correct instructions to build the image, including cloning the needed repository, building the project, and running the application. The second version, however, the repository is cloned directly in this repository and not cloned by the docker itself. This technical report outlines the steps involved to finalize this Part 1.

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.

### To properly complete the second part of this Class Assignment:
1. Created two dockerfiles, one for the database and another for the web application:
```dockerfile
#Dockerfile for the database
# Use Ubuntu as the base image
FROM ubuntu

# Install OpenJDK, unzip, and wget
RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless unzip wget

# Create and set the working directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Download H2 Database and place it in /opt
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar

# Expose the necessary ports
EXPOSE 8082 9092

# Start the H2 Server
CMD ["java", "-cp", "/opt/h2.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
````

```dockerfile
#Dockerfile for the web application
FROM openjdk:17-jdk-slim

# Install additional utilities
RUN apt-get update -y && apt-get install -y git unzip

# Create and set the working directory
RUN mkdir -p /app
WORKDIR /app

# Clone the private repository to the container
RUN git clone https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git

# Set the working directory to the Spring Boot application directory
WORKDIR /app/devops-23-24-JPE-PSM-1231822/CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic

# Ensure the gradlew script is executable and build the application
RUN chmod +x ./gradlew && ./gradlew clean build

# Configure the container to run as an executable
ENTRYPOINT ["./gradlew"]
CMD ["bootRun"]
````
2. Then created a docker-compose.yml file to run both dockerfiles in parallel:
```yaml
services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - ./data:/usr/src/data-backup
    networks:
      default:
        ipv4_address: 192.168.33.11

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    ports:
      - "8080:8080"
    networks:
      default:
        ipv4_address: 192.168.33.10
    depends_on:
      - "db"

networks:
  default:
    ipam:
      driver: default
      config:
        - subnet: 192.168.33.0/24
````
3. To create the image of the docker, used the command:
```bash
docker-compose up
````
4. To properly publish the images in Docker Hub and make them available to the Docker Desktop, the following steps were taken:
- First, logged in to Docker Hub:
```bash
docker login
````
- Then, check the if of the images:
```bash
docker images
````
- Tagged the images:
```bash
docker tag <image_id> anaspinto/db
docker tag <image_id> anasinto/web
````
- Pushed the images to Docker Hub:
```bash
docker push anaspinto/db
docker push anaspinto/web
````

With this command, both images were created, as well as the containers, and the app was running.
5. Then, when writing localhost:8080 on the browser, the app of the CA2 Part 2 was running, and the database was connected to the app.

## Conclusions
Upon completion, the application was successfully deployed and accessible via localhost:8080, demonstrating seamless integration between the Spring Boot backend and the H2 database server running within Docker containers. This setup not only met the assignment requirements but also showcased the benefits of containerization for developing and deploying modern applications.