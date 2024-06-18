# Techincal Report Of Class Assignment 5

## General Introduction
The goal for this class assignment is to create a Jenkins pipeline to automate the build and deployment of the web application created in the previous class assignments. The two class assignments that will be used in this pipeline are the Class Assignment 2 part 1 and part 2.

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.

### To successfully complete the 2 parts of this class assignment, the docker in docker (dind) was used and the Jenkins was connected to it. The following steps were taken:
1. The instructions provided in the following link were applied: https://www.jenkins.io/doc/book/installing/docker/;
2. Create a network in Docker:
```bash
docker network create jenkins
```
3. Then, the powershell was used to continue with the following steps to create a docker container with Jenkins:
```powershell
docker run --name jenkins-docker --rm --detach `
   --privileged --network jenkins --network-alias docker `
   --env DOCKER_TLS_CERTDIR=/certs `
   --volume jenkins-docker-certs:/certs/client `
   --volume jenkins-data:/var/jenkins_home `
   --publish 2376:2376 `
   docker:dind
```
This way, both of the containers are correctly connected to each other via bridge network.
The command runs a Dind container with the name jenkins-docker in detached mode, with the network jenkins. The environment variable DOCKER_TLS_CERTDIR is set to /certs, and the volume jenkins-docker-certs is mounted to /certs/client. The volume jenkins-data is mounted to /var/jenkins_home, and the port 2376 is published to the host.

4. The following Dockerfile customizes a Jenkins image by installing the Docker CLI and specific Jenkins plugins. The installation steps include setting up Docker's repository and key, then installing the necessary packages. Finally, it switches back to the Jenkins user to ensure Jenkins runs with the appropriate permissions. The Dockerfile is created in the CA5 directory:
```dockerfile
FROM jenkins/jenkins:2.452.2-jdk17

USER root

RUN apt-get update && apt-get install -y lsb-release

RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg
RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli

USER jenkins

RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"
```
#### Explanation:
This Dockerfile is based on the jenkins/jenkins:2.452.2-jdk17 image. It switches to the root user to install the lsb-release package and the Docker CLI. It sets up Docker's GPG key and repository, installs the Docker CLI, then switches back to the Jenkins user. Finally, it installs the "blueocean" and "docker-workflow" plugins for enhanced UI and Docker pipeline capabilities.

5. Then, a new Docker image is created based on the above Dockerfile. The following docker run command is used to create and start a new container from the custom image:
```bash
docker run --name jenkins-blueocean --restart=on-failure --detach `
  --network jenkins --env DOCKER_HOST=tcp://docker:2376 `
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 `
  --volume jenkins-data:/var/jenkins_home `
  --volume jenkins-docker-certs:/certs/client:ro `
  --publish 8080:8080 --publish 50000:50000 myjenkins-blueocean:2.452.2-1
```
This command runs a container using the custom Jenkins image created from the Dockerfile. It sets up the necessary environment for Jenkins to interact securely with Docker, including network settings, environment variables, volume mounts for data persistence and certificates, and port mappings for web and agent access.
 
6. The next step was to go to localhost:8080, that opens the Jenkins page, and then the password was obtained in the terminal by typing:
````bash
  $ docker logs <container-id or name> 
````
- The password was pasted on the Jenkins page, and the "Continue" button was clicked.
- The "Install suggested plugins" was clicked and an admin user was created.
- After that, in the credential section, a new credential was added,as well as the dockerhub name and password and then an ID was defined.
- Then, already in the application, the "Manage Jenkins" was clicked to ensure that the JSON API was enabled.

Before going to the next step, check if all the plugins are correctly installed. In this case, had to install the following plugin:
````bash
jenkins-plugin-cli --plugins json-path-api:2.9.0-58.v62e3e85b_a_655
````

### To properly complete the first part of this Class Assignment:
With Jenkins already functional, a new job was created. To do that click on the "New Item" button, then the name of the job (in this case in particular, gradle_basic_demo) was added and the "Pipeline" was selected. After that, the "OK" button was clicked.
In the pipeline section, select the pipeline script from SCM, and insert the repository URL. On the branch specification, ensure the main branch is selected, in this case is main. The pipeline was save.

1. Then, created a Jenkinsfile in the CA2/Part1 directory:
```groovy
pipeline {
    agent any

    stages {
    //What repository to checkout
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git'
                dir('CA2/Part1/gradle_basic_demo') {
                }
            }
        }
        stage('Assemble') {
            steps {
                dir('CA2/Part1/gradle_basic_demo') {
                    sh 'chmod +x gradlew'
                    sh './gradlew assemble'
                }
            }
        }
        stage('Test') {
            steps {
                dir('CA2/Part1/gradle_basic_demo') {
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {
                dir('CA2/Part1/gradle_basic_demo') {
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}
```
#### Explanation of the Jenkinsfile:
- The pipeline is defined with the agent any, meaning that the pipeline can run on any available agent.
- The stages are defined with the following steps:
  - The checkout stage is responsible for cloning the repository.
  - Then, the project is assembled using gradle, not forgetting to give the necessary permissions.
  - After that, the tests are run and the result is published in the JUnit report.
  - It is then archived the artifacts, in this case, the jar file.

2. Then, the Jenkinsfile was added to the repository and the pipeline was run. The pipeline was successful.

### To properly complete the second part of this Class Assignment:
1. Followed the same steps to create a new job with a new pipeline in Jenkins, with the name of "react_and_spring_data_rest_basic".
2. Created a Jenkinsfile in the CA2/Part2 directory:
```groovy
pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'DHCred'
        DOCKER_IMAGE = "anaspinto/ca5part2:${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Check out the repository
                    checkout([$class: 'GitSCM',
                              branches: [[name: 'main']],
                              userRemoteConfigs: [[url: 'https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git']]])
                }
            }
        }

        stage('Assemble') {
            steps {
                script {
                    // Navigate to the project directory and run Gradle assemble
                    dir('CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic') {
                        sh 'chmod +x ./gradlew'
                        sh './gradlew clean assemble'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Navigate to the project directory and run Gradle test
                    dir('CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic') {
                        sh './gradlew test'
                    }
                }
            }
            post {
                always {
                    // Archive test results
                    junit 'CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic/build/test-results/test/*.xml'
                }
            }
        }

        stage('Javadoc') {
            steps {
                script {
                    // Navigate to the project directory and generate Javadoc
                    dir('CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic') {
                        sh './gradlew javadoc'
                    }
                }
            }
            post {
                always {
                    // Publish Javadoc
                    publishHTML(target: [
                            allowMissing         : false,
                            alwaysLinkToLastBuild: false,
                            keepAll              : true,
                            reportDir            : 'CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic/build/docs/javadoc',
                            reportFiles          : 'index.html',
                            reportName           : 'Javadoc'
                    ])
                }
            }
        }

        stage('Archive') {
            steps {
                script {
                    // Archive the built WAR file
                    archiveArtifacts artifacts: 'CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic/build/libs/*.war', fingerprint: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    dir('CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic') {
                        // Copy WAR file to Docker build context
                        sh 'cp build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war .'

                        // Dynamically generate Dockerfile       // To make the App functional by connecting to the database the ENV line was added

                        def dockerfileContent = """
                            FROM tomcat:10.0.20-jdk17-temurin

                            COPY *.war /usr/local/tomcat/webapps/
                            ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:jpadb
                        """
                        writeFile file: 'Dockerfile', text: dockerfileContent

                        // Build Docker image
                        script {
                        docker.build("${DOCKER_IMAGE}")
                        }
                    }
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        docker.image("${DOCKER_IMAGE}").push()
                    }
                }
            }
        }
    }

}
```
#### To explain in detail the Jenkinsfile:
- The pipeline is defined with the agent any, meaning that the pipeline can run on any available agent.
- The environment block is used to define the Docker credentials ID and the Docker image name.
- The stages are defined with the following steps:
  - The checkout stage is responsible for cloning the repository.
  - Then, the project is assembled using gradle, not forgetting to give the necessary permissions.
  - After that, the tests are run and the result is published in the JUnit report.
  - The Javadoc is generated and published.
  - It is then archived the artifacts, in this case, the war file.
  - The Docker image is built, and the Dockerfile is dynamically generated.
  - The Docker image is pushed to the Docker Hub.

The image can be found in the following link: https://hub.docker.com/repository/docker/anaspinto/ca5part2

Then, this Jenkinsfile was added to the repository and the pipeline was run. The pipeline was successful.

#### To add the extra of doing this part of the assignment using a docker-compose file:
1. A docker-compose.yml file was created in the CA5 directory:
```yaml
version: '3'

services:

  docker:
    container_name: docker
    image: docker:dind
    restart: always
    privileged: true
    networks:
      - jenkins
    environment:
      - DOCKER_TLS_CERTDIR=/certs
    volumes:
      - jenkins-docker-certs:/certs/client
      - jenkins-data:/var/jenkins_home
    ports:
      - "2376:2376"

  jenkins-blueocean:
    container_name: jenkins-blueocean
    build:
      context: .
      dockerfile: Dockerfile
    restart: on-failure
    networks:
      - jenkins
    environment:
      - DOCKER_HOST=tcp://docker:2376
      - DOCKER_CERT_PATH=/certs/client
      - DOCKER_TLS_VERIFY=1
    volumes:
      - jenkins-data:/var/jenkins_home
      - jenkins-docker-certs:/certs/client:ro
    ports:
      - "8080:8080"
      - "50000:50000"

networks:
  jenkins:
    driver: bridge

volumes:
  jenkins-data:
    external:
      name: jenkins-data  # Use existing volume named 'jenkins-data'
  jenkins-docker-certs:
    external:
      name: jenkins-docker-certs  # Use existing volume named 'jenkins-docker-certs'
```
#### Explanation of the docker-compose.yml file:
This Docker Compose file sets up a multi-container environment for Jenkins with Docker-in-Docker (dind). It specifies two services: docker, which runs a privileged Docker daemon using the docker:dind image, and jenkins-blueocean, which builds and runs a Jenkins Blue Ocean instance from a Dockerfile located in the current directory. Both services are linked through a custom bridge network called jenkins. The docker service is configured with volumes for Docker TLS certificates and Jenkins data, and it publishes port 2376 for Docker communication. The jenkins-blueocean service is designed to utilize the Docker daemon for building and deploying applications, with volumes mounted for the Jenkins home directory and Docker TLS certificates, and it exposes ports 8080 and 50000. This setup ensures that Jenkins can securely and persistently interact with the Docker daemon.

2. Then, the docker-compose was used to build the containers:
```bash
docker-compose up
```
3. The docker-compose was committed to the repository, and the Jenkins pipeline was updated to include the docker-compose command. The pipeline was run. The pipeline was successful.


