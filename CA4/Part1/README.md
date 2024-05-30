# Techincal Report Of Class Assignment 4 Part 1

## General Introduction
The goal for this first part of this class assignment is to display the usage of containers. This first part is composed of two versions, where the first version is to create a Dockerfile that provides all the correct instructions to build the image, including cloning the needed repository, building the project, and running the application. The second version, however, the repository is cloned directly in this repository and not cloned by the docker itself. This technical report outlines the steps involved to finalize this Part 1.

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.


### To properly complete the first version of the part1 of this Class Assignment:
1. Created a dockerfile that provides all the correct instructions to build the image:
````dockerfile
##First stage: Build the application
FROM gradle:jdk17 as builder
LABEL author="Ana Sofia Pinto"

WORKDIR /ca4-part1

##Clone the Git repository
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

##Change the working directory to the root directory of the cloned repository
WORKDIR /ca4-part1/gradle_basic_demo
RUN chmod +x gradlew

##Build the project
RUN ./gradlew build

##Second stage: Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /ca4-part1

##Copy the built JAR file from the first stage
COPY --from=builder /ca4-part1/gradle_basic_demo/build/libs/*.jar ca4-part1.jar

##Specify the command to run the application
##Replace <server port> with the actual server port you want to use
ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]
````
2. Then used the following command on the bash terminal:
````bash
docker build -t ca4-part1 .
````
With this command, successfully made a docker build of the image, so if I check the docker desktop app, in the section "images" I can see the image ca4-part1.
3. After that, I ran the following command: 
````bash
docker run -p 59001:59001 ca4-part1. 
```` 
With this command, I was able to run the image, leading to an automatic creation of a container, which I can see in the docker desktop app, in the section "containers".
4. While the image was running, in the git bash terminal I see that "The chat server is running..." 
5. If I open a different git bash terminal, I placed myself on the `Ana Sofia@Ana-Sofia MINGW64 ~/Desktop/devops-23-24-JPE-PSM-1231822/CA2/Part1/gradle_basic_demo` directory, and then use the command: ./gradlew runClient:
````bash
cd ~/Desktop/devops-23-24-JPE-PSM-1231822/CA2/Part1/gradle_basic_demo
./gradlew runClient
````
6. I can see that the chat application is working properly, since it opens the java window that allows me to chat with the server.
7. To successfully send the docker image to the docker desktop, had to make docker login (by signing in with my GitHub account) and then used the command "docker images". With that command, I've obtained the following result: 
````bash
docker images
````
````docker
REPOSITORY               TAG       IMAGE ID       CREATED          SIZE
ca4-part1                latest    ac853b19bba6   27 minutes ago   410MB
jamj2000/oracle-xe-21c   latest    3697feff54f9   22 months ago    6.53GB 
````
8. After that, I used the command to tag the image: 
````bash
docker tag ac853b19bba6 anaspinto/ca4-part1:ca4-part1-version1 
````
Later on, I was able to send the image to the docker desktop by pushing it with the command: 
````bash
$ docker push anaspinto/ca4-part1:ca4-part1-version1
````


### To properly complete the second version of the part1 of this Class Assignment:
1. Copy the repository https://bitbucket.org/pssmatos/gradle_basic_demo/ to the repository of this class assignment, to properly have the server running on the host computer and the client will later be run on the docker container.
2. Created a dockerfile that provides all the correct instructions to build the image:
````dockerfile
# First stage: Build the application
FROM gradle:jdk17 as builder
LABEL author="Ana Sofia Pinto"

WORKDIR /ca4-part1


COPY ./gradle_basic_demo .

RUN ./gradlew clean build


# Second stage: Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /ca4-part1

# Copy the built JAR file from the first stage
COPY --from=builder /ca4-part1/build/libs/*.jar ca4-part1.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]
````
The clean build command was used since the jdk used in the CA2, Part1 was 21 and here I used 17, so clean build had to occur, otherwise, it wouldn't be necessary if the jdk was the same.
3. Then proceeded to make the same commands as in the first version, to build the image, tag it and finally run it, guaranteeing that the server was running on the host computer and the client was running on the docker container.


