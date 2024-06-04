# Techincal Report Of Class Assignment 4 Part 2

## General Introduction
The goal for this second part of this class assignment is to display the usage of containers. This second part is focused on the creation of a Dockerfile for the database and another for the web application, and then use a docker-compose.yml file to run both dockerfiles in parallel. This technical report outlines the steps involved to finalize this Part 2.

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.

### To properly complete the second part of this Class Assignment:
1. Created two dockerfiles in the react-and-spring-data-rest-basic directory inside the CA2/Part2 directory, one for the database and another for the web application:
```dockerfile
#Dockerfile for the database
FROM gradle:jdk21
WORKDIR /opt/h2
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O h2.jar
EXPOSE 8082
EXPOSE 9092
CMD ["java", "-cp", "h2.jar", "org.h2.tools.Server", "-ifNotExists", "-web", "-webAllowOthers", "-webPort", "8082", "-tcp", "-tcpAllowOthers", "-tcpPort", "9092"]
````

```dockerfile
#Dockerfile for the web application
FROM gradle:jdk21
WORKDIR /app
COPY . /app
EXPOSE 8080
CMD ["java", "-jar", "build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar"]
````
2. Then, on the same directory, created a docker-compose.yml file to run both dockerfiles in parallel:
```yaml
services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    container_name: h2-db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - h2-data:/opt/h2-data
      - db-backup:/backup

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    container_name: spring-web
    ports:
      - "8080:8080"
    depends_on:
      - db

volumes:
  h2-data:
    driver: local
  db-backup:
    driver: local
````
These steps were made inside the CA2/Part2 directory, to avoid any irregularities to run the requested app.
3. Then, used the following command on the bash terminal:
```bash
docker-compose up
````

With this command, both images were created, as well as the containers, and the app was running.
4. Then, when writing localhost:8080 on the browser, the app of the CA2 Part 2 was running, and the database was connected to the app.

5. To correctly make a backup of the database, the following command was used:
```bash
docker exec -it h2-db /bin/sh -c 'cp /opt/h2-data/h2.mv.db /backup/h2.mv.db'
````

If a copy of the database in the local machine, the following command should be used:
```bash
docker cp 0c243d226fc2:/opt/h2/jpadb.mv.db .
````
Being the "0c243d226fc2" the container ID, obtained by using the command "docker ps".

6. Publish the newly produced Docker images:
```bash
docker tag a63a17545577 anaspinto/ca2-part2:ca4-part2-db
docker tag 6753b5aad367 anaspinto/ca2-part2:ca4-part2-web
````
Followed by the command:
```bash
docker push anaspinto/ca2-part2:ca4-part2-db
docker push anaspinto/ca2-part2:ca4-part2-web
````
7. To verify the created images, you can visit the Docker Hub in the following address: https://hub.docker.com/u/anaspinto

## Comparison between the use of Docker vs the use of Kubernetes
Docker is a platform for developing, shipping, and running applications in containers. It simplifies the creation and management of containerized applications.
Kubernetes is an open-source platform for automating deployment, scaling, and operations of application containers across clusters of hosts. It is designed to manage containerized applications in a clustered environment.
They are essentially complementary technologies, with Docker providing the containerization technology and Kubernetes providing the orchestration and management of those containers, meaning Docker provides the foundation for containerization, while Kubernetes builds on that foundation to manage containers at scale. In many real-world scenarios, both are used together.


## Conclusions
Upon completion, the application was successfully deployed and accessible via localhost:8080, demonstrating seamless integration between the Spring Boot backend and the H2 database server running within Docker containers. This setup not only met the assignment requirements but also showcased the benefits of containerization for developing and deploying modern applications.