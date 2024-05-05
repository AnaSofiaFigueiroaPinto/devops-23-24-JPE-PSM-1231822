# Techincal Report Of Class Assignment 3 Part 1

## General Introduction
The goal for this first part of this class assignment is to provide an introduction to virtualization. This first part of the assignment is to correctly setup a virtual machine, test if it is possible to run the CA1 and CA2 assignments on a VirtualBox virtual machine, all the while taking into consideration the needed dependencies and configurations. This technical report outlines the steps involved to finalize this Part 1.

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.

## This first part was divided on 3 main tasks:
1. Apply the "Hands-On" instructions (from slide 179 to 188) provided on the pdf manual of the theoretical class.
2. Run the applications from the Ca1 and CA2 assignments on the virtual machine.
3. Create a technical report with the steps taken to complete the assignment.

### To complete the first task, the following steps were taken:
1. The VirtualBox software was downloaded from the official website. Initially, the version 7.0.16 was installed, but it presented some bugs that the VirtualBox communicated that caused some blue screens when running, so I had to remove this version, and installed a slightly older one, the 7.0.12 version.
2. Then, I've downloaded the Ubuntu 20.04.6 LTS version from the official website.
3. Adjusted the VirtualBox settings to all the requirements requested on the manual.
4. After that, I've started the VM and installed the Ubuntu on it.
5. After all of this was set, I've followed the commands provided on the manual, namely:
```bash
sudo apt update
sudo apt install net-tools
sudo nano /etc/netplan/00-installer-config.yaml 
```
This latest command is slightly different from the one provided on the manual, but it had to be updated since I've used a more recent version of Ubuntu. The manual command was:
```bash
sudo nano /etc/netplan/01-netcfg.yaml
```
6. After this, I've followed the rest of the commands provided on the manual:
```bash
sudo netplan apply
sudo apt install openssh-server
sudo nano /etc/ssh/sshd_config
```
the line PasswordAuthentication yes was uncommented
```bash
sudo service ssh restart
sudo apt install vsftpd
sudo nano /etc/vsftpd.conf
```
the line write_enable=YES was uncommented
```bash
sudo service vsftpd restart
```
7. Since the SSH server had been installed, I've continued the commands in the terminal of my local machine, by connecting to the VM, using the command:
```bash
ssh anaspinto@192.168.56.5
```
8. After this, I've followed the rest of the commands provided on the manual:
```bash
sudo apt install git
sudo apt install openjdk-17-jdk-headless
```
The last final contains the "headless" word, since I'm using a virtual machine, and it doesn't have a graphical interface.
9. After this, I've cloned the repository of the tutorial and ran the application:
```bash
git clone https://github.com/spring-guides/tut-react-and-spring-data-rest.git
cd tut-react-and-spring-data-rest/basic
./mvnw spring-boot:run
```
10. The application was running successfully, and I've accessed it on the browser, using the address http://192.168.56.5:8080/.

### To complete the second task, the following steps were taken:
1. The first step was to clone my personal repository to the VM:
```bash
git clone https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git
```
2. Then, for each CA folder, I´ve navigated to the respective folder, build and ran the application:
```bash
cd devops-23-24-JPE-PSM-1231822/CA1/tut-react-and-spring-data-rest/basic
./mvnw spring-boot:run
```
```bash
cd devops-23-24-JPE-PSM-1231822/CA2/Part1/gradle_basic_demo
./gradlew bootRun
```
To be able to correctly run both applications, I had to update some permissions on the gradle and maven wrappers, since they only had read permission, and needed to have the execute permission. To do so, I've used the following commands:
```bash
chmod +x mvnw
chmod +x gradlew
```
3. After this, and to be able to run the chat application, I had to run the server on the VM, but the client had to be run on the local machine itself. To do so, I've used the following commands:
- To run the server on the VM:
```bash
cd devops-23-24-JPE-PSM-1231822/CA2/Part1/gradle_basic_demo
./gradlew runServer
```
- To run the client on the local machine:
```bash
cd devops-23-24-JPE-PSM-1231822/CA2/Part1/gradle_basic_demo
./gradlew runClient --args="192.168.56.5 59001"
```
This last command was based on what is described in the build.gradle file, in the task runClient, to know exactly what port to use.
4. Then, the chat application was successfully running. 

This separation between server and client had to occur since the VM does not have a graphical interface to support the client side, making it impossible to run on the VM. Otherwise, a "headless" error would occur.
