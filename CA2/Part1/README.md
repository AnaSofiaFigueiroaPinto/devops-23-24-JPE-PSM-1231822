# Techincal Report Of Class Assignment 2 Part 1

## General Introduction
The objective of Part 1 of the assignment is to obtain hands-on experience with Gradle. To achieve that, a simple example application was used. 
This technical report outlines the steps involved to finalize this Part 1, including setting up the example application, performing various tasks using Gradle, adding unit tests, and creating backups and archives of the application sources.The purpose of this class assignment was to use personal repositories to introduce the build tools using gradle, all the while, keeping in mind the correct git commands to handle all of the requests in the git bash terminal.
Further details can be found at [https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822]. 

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.

### The following steps were taken to complete the assignment:
1. The following [repository](https://bitbucket.org/pssmatos/gradle_basic_demo/) was cloned to the local machine.
2. To get to the project directory in the terminal, the following command was used:
```bash
   cd path/to/gradle_basic_demo
   ```
3. The application was copied into a new folder (CA2/Part1):
```bash
   cp -r . ../CA2/Part1
   cd ../CA2/Part1
   ```
4. Committed the changes:
```bash
   git add .
   git commit -m "Message that addresses the changes made in this commit"
   git push origin main
   ```
5. ##### The first step of this assignment was implemented (add a new task in the build.gradle file to start the server):
```gradle
   task runServer(type:JavaExec, dependsOn: classes) {
    group = "DevOps"
    description = "Launches a chat client that connects to a server on localhost:59001 "

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'}

   ```
- This task will execute a Java class that will start a server on localhost:59001. The server will be listening for incoming connections from clients.

6. ##### Then, I've implemented the second step of the assignment (add a new test class and corresponding unit test):
Started by creating a new folder:
```bash
   mkdir src/test/java/basic_demo
   ```
- Then, created a new test class:
```bash
   touch src/test/java/basic_demo/AppTest.java
   ```
- The following code was added to the AppTest.java file:
```java
   package basic_demo;

    import org.junit.Test;

    import static org.junit.Assert.*;

    public class AppTest {

        @Test
        public void testAppHasAGreeting() {
            App classUnderTest = new App();
            assertNotNull("app should have a greeting", classUnderTest.getGreeting());
        }
   }
   ```
- Finally, added the dependencies to the build.gradle file:
```gradle
   dependencies {
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
    implementation group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.11.2'
    implementation 'junit:junit:4.12'
   }
   ```
7. These changes were then committed to the repository:
```bash
   git add .
   git commit -m "Message that describes the update made to the repository"
   git push origin main
   ```
8. While in the folder of basic_demo, I've compiled the project:
```bash
   ./gradlew build
   ```
9. Then, I've started the server, since I wanted to test the application with the new updates:
```bash
   ./gradlew runServer
   ```
- In a new terminal, I've started the client:
```bash
   ./gradlew runClient
   ```
- A screen appeared asking for a username. After entering a username, the client was connected to the server. The server then acknowledged the connection and the client was able to send messages to the server. The server then broadcasted the messages to all other clients connected to the server.

10. #### After these steps, I've added the next phase of the assignment, that was to add a Copy task to the build.gradle file:
```gradle
   task backupSources(type: Copy) {
        group = 'Backup'
        description = 'Make a backup of the application sources'

        // Define the source directory
        def sourceDir = file('src')

        // Define the destination directory for the backup
        def backupDir = file("${buildDir}/backup/src")

        // Set the source and destination directories for the copy operation
        from sourceDir
        into backupDir
}
   ```
- Then, I compiled the project again:
```bash
   ./gradlew build
   ```
11. #### Finally, I've added the last step of the assignment, that was to add a Zip task to the build.gradle file:
```gradle
   task zipSources(type: Zip) {
    group = 'Archive'
    description = 'Creates a zip archive of the application sources'

    def sourceDir = file('src')

    def zipFile = file("${buildDir}/archives/sources.zip")

    from sourceDir

    archiveFileName = zipFile.name
    destinationDirectory = zipFile.parentFile
}
   ```
- Then, I compiled the project again:
- Then, I compiled the project again:
```bash
   ./gradlew build
   ```
12. After this, I've committed the changes to the repository:
```bash
   git add .
   git commit -m "Message that describes the update made to the repository"
   git push origin main
   ```
