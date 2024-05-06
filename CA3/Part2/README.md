# Techincal Report Of Class Assignment 3 Part 2

## General Introduction
The goal for this second part of this class assignment is to create and run two different virtual machines of VirtualBox, simultaneously, while using Vagrant.

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with a unique number, that should be used in the commit message that shows any editions related to the issue.

## This second part was divided on 4 main tasks:
1. Install Vagrant on the computer.
2. Configure the Vagrantfile copied to this repository accordingly.
3. Run Vagrant to check if everything is working properly in the correct path.
4. Create a technical report with the steps taken to complete the assignment.

### To complete the first task, the following steps were taken:
1. The Vagrant software was downloaded from the official website.
2. The software was installed on the computer.

### To complete the second task, the following steps were taken:
1. First, the repository https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo.git was cloned to my local machine.
2. Then, the Vagrantfile was copied to the repository of this assignment, using the following commands:
```bash
git show master:~/Desktop/Vagrant/vagrant-multi-spring-tut-demo/ > Vagrantfile
git add ~/Desktop/devops-23-24-JPE-PSM-1231822/CA3/Part2/Vagrantfile
```
3. Then, the Vagrantfile was configured. Considering the original file, the file was adjusted to this:
```ruby
# See: https://manski.net/2016/09/vagrant-multi-machine-tutorial/
# for information about machine names on private network
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connect to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setting H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git
      cd ~/devops-23-24-JPE-PSM-1231822/CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      # To deploy the war file to tomcat9 do the following command:
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
  end
end
```
The main changes were:
- using a different ubuntu version (used the focal64, instead of the bionic64);
- added the installation of the openjdk-17-jdk-headless package;
- update the last lines as requested to the following:
```ruby
      git clone https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git
      cd ~/devops-23-24-JPE-PSM-1231822/CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      # To deploy the war file to tomcat9 do the following command:
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
```
Felt the need to change this last line since, for some reason, the command `sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps` was not working properly.

### To complete the third task, the following steps were taken:
1. Opened the terminal and navigated to the repository of this assignment:
```powershell
cd ~/Desktop/devops-23-24-JPE-PSM-1231822/CA3/Part2
```
2. Then, the command `vagrant up` was executed to start the VMs:
```powershell
PS C:\Users\Ana Sofia\Desktop\devops-23-24-JPE-PSM-1231822\CA3\Part2> vagrant up
```
3. After the VMs were up and running, introduced in the browser the following address: http://192.168.56.10:8080/ to access the application.
4. With the application successfully running, the command `vagrant halt` was executed to shut down the VMs:
```powershell
PS C:\Users\Ana Sofia\Desktop\devops-23-24-JPE-PSM-1231822\CA3\Part2> vagrant halt
```
5. Finally, the command `vagrant destroy web` and `vagrant destroy db` were executed to remove both of the VMs:
```powershell
PS C:\Users\Ana Sofia\Desktop\devops-23-24-JPE-PSM-1231822\CA3\Part2> vagrant destroy web
PS C:\Users\Ana Sofia\Desktop\devops-23-24-JPE-PSM-1231822\CA3\Part2> vagrant destroy db
```


## Alternative version - using VMware:
To correctly run the alternative version, VMware requires to have the desktop version installed.
The Vagrantfile was also configured to use VMware. The file was adjusted to this:
```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connect to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL

    # Use VMware provider
    db.vm.provider "vmware_desktop" do |v|
    v.memory = 1024
    end
  end

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memory for this VM
    web.vm.provider "vmware_desktop" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822.git
      cd ~/devops-23-24-JPE-PSM-1231822/CA2/Part2/react-and-spring-data-rest-basic/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      # To deploy the war file to tomcat9 do the following command:
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
   end
end
```

Then, the same steps used for the VirtualBox, were also used in the powershell to run the VMs using VMware.

### Main difference between the VirtubalBox and VMware:

Regarding the feature set, VirtualBox provides a comprehensive set of features for desktop virtualization, including support for various guest operating systems, snapshots, cloning, and virtual machine groups. However, it may lack some advanced features compared to VMware.
On the other hand, VMware offers a wide range of features tailored for both desktop and enterprise virtualization. VMware Workstation provides advanced functionalities such as VM encryption, VM cloning with linked clones, advanced networking configurations, and integration with VMware vSphere.
As far as performance and stability are concerned, both VirtualBox and VMware are known for their stability and reliability. However, VMware is often praised for its performance optimization and resource management, making it a preferred choice for demanding workloads such as enterprise environments.
Regarding management tools, VirtualBox includes a simple and user-friendly graphical user interface (GUI) for managing virtual machines. It also provides command-line tools for advanced configuration and automation.
VMware offers robust management tools such as VMware Workstation Pro and VMware vCenter Server. These tools provide centralized management, monitoring, and automation capabilities for large-scale virtualized environments.
Taking into consideration integration and ecosystem VirtualBox integrates well with open-source technologies and is widely used by developers, enthusiasts, and small businesses. It has a vibrant community that contributes to its development and supports various guest operating systems.
On the other hand, VMware has a strong presence in the enterprise market and offers extensive integration with VMware vSphere, VMware Cloud, and other VMware products. It provides comprehensive support, documentation, and training resources for businesses of all sizes.






