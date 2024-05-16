To properly complete the first version of the part1 of this Class Assignment:
1. Created a dockerfile that provides all the correct instructions to build the image.
2. Then used the following command on the bash terminal: docker build -t ca4-part1 . (actually used the . )
With this command, successfully made a docker build of the image, so if I check the docker desktop app, in the section "images" I can see the image ca4-part1.
3. After that I ran the following command: docker run -p 59001:59001 ca4-part1. With this command, I was able to run the image, leading to an automatic creation of a container, which I can see in the docker desktop app, in the section "containers".
4. With the image running, in the git bash terminal I see that "The chat server is running..." and if I open a different git bash terminal, place myself on the `Ana Sofia@Ana-Sofia MINGW64 ~/Desktop/devops-23-24-JPE-PSM-1231822/CA2/Part1/gradle_basic_demo` directory, and then use the command: ./gradlew runClient, I can see that the chat application is working properly, since it opens the java window that allows me to chat with the server.

5. To successfully send the docker image to the docker desktop, had to make docker login (by signing in with my github account) and then used the command "docker images". With that command, I've obtained the following result: 
6. REPOSITORY               TAG       IMAGE ID       CREATED          SIZE
   ca4-part1                latest    ac853b19bba6   27 minutes ago   410MB
   jamj2000/oracle-xe-21c   latest    3697feff54f9   22 months ago    6.53GB 
After that, I used the command: docker tag ac853b19bba6 anaspinto/ca4-part1:ca4-part1-version1 to tag the image. After that, I was able to send the image to the docker desktop by pushing it with the command: $ docker push anaspinto/ca4-part1:ca4-part1-version1


Version 2 - o cleanBuild é utilizado visto que no CA2, Part1 o jdk utilizado foi o 21 e aqui utilizei o 17, logo tem cleanBuild tem que ocorrer, doutra forma, não sei necessário se o jdk fosse o mesmo.

