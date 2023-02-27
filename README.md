# spring-boot-jpa-docker-jenkins-pipeline
<!-- PROJECT SHIELDS -->

<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline.svg?style=for-the-badge
[contributors-url]: https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline.svg?style=for-the-badge
[forks-url]: https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/network/members
[stars-shield]: https://img.shields.io/github/stars/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline.svg?style=for-the-badge
[stars-url]: https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/stargazers
[issues-shield]: https://img.shields.io/github/issues/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline.svg?style=for-the-badge
[issues-url]: https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/ye-win-1a33a292/


<h3 align="center">
Overview
<img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/spring_boot_docker_jenkins.png" /><br/>
</h3>

# spring-boot-jpa-docker-jenkins-pipeline
* This is sample spring boot jpa application which use docker and Jenkins pipeline.
* Nowadays, Using CI/CD like Jenkins, CircleCI, Bamboo, Gitlab or etc. servers is widely use and helping a lot in application deployment.
* This project is communicate with this [Jenkins Server](https://github.com/yewin-mm/jenkins-server) as building the application.
* Jenkins will help you automatic build and push and deploy your application with single click.
* You can check out below [Before you begin](#before-you-begin), [prerequisites][#prerequisites] sections and step-by-step guide in [Instruction](#instruction).

<!-- TABLE OF CONTENTS -->
## Table of Contents
- [About The Project](#about-the-project)
    - [Built With](#built-with)
- [Getting Started](#getting-started)
    - [Before you begin](#before-you-begin)
    - [Clone Project](#clone-project)
    - [Prerequisites](#prerequisites)
    - [Instruction](#instruction)
      -  [Check Properties file](#check-properties-file)
      -  [Check Jenkins file](#check-jenkins-file)
      -  [Push to GitHub](#push-github)
      -  [Build Application Through Jenkins](#build-app)
        -  [Copy Pipeline script and Add in Jenkins](#copy-script)
        -  [Build](#build)
        -  [Check Error](#check-error)
        -  [Check Docker Hub](#check-dockerhub)
        -  [Check Test Result Chart](#check-test-result)
        -  [Test New Image Version in Docker Hub](#test-new-image)
- [Contact Me](#contact)
- [Contributing](#Contributing)


<a name="about-the-project"></a>
## ⚡️About The Project
This is the sample project to automatically build application and push image to registry server using Jenkins and pipeline <br>
This project used this [Jenkins Server](https://github.com/yewin-mm/jenkins-server) for building this project and pushing to registry.


<a name="built-with"></a>
### 🪓 Built With
This project is built with
* [Docker](https://www.docker.com/products/docker-desktop/)
* [Jenkins Server](https://github.com/yewin-mm/jenkins-server)


<a name="getting-started"></a>
## 🔥 Getting Started
This project purpose is to use `Jenkins Pipeline` for automated building image and pushing image to registry and that let build java application with Spring Boot framework and using JPA for database operation and use `Dockerfile` and `docker-compose` file for creating docker image.<br>
There are two type of pipeline script style,
1. Scripted Pipeline
2. Declarative Pipeline <br>
See the [Prerequisites](#prerequisites) sections for basic knowledge and go as per below [Instruction](#instruction) section.


<a name="before-you-begin"></a>
### 🔔 Before you begin
If you are new in Git, GitHub and new in Spring Boot configuration structure, <br>
You should see basic detail instructions first in here [Spring Boot Application Instruction](https://github.com/yewin-mm/spring-boot-app-instruction)<br>
If you are not good enough in basic API knowledge with Java Spring Boot, Docker basic, Docker Compose basic and other spring basic knowledge, you should learn below example projects first. <br>
Click below links.
* [Spring Boot Sample CRUD Application](https://github.com/yewin-mm/spring-boot-sample-crud) (for sample CRUD application)
* [Reading Values from Properties files](https://github.com/yewin-mm/reading-properties-file-values) (for reading values from properties files)
* [Spring Boot Docker Sample](https://github.com/yewin-mm/spring-boot-docker-sample) (to get basic docker commands)
* [MySQL Container](https://github.com/yewin-mm/mysql-docker-container)
* [Spring Boot JPA Docker](https://github.com/yewin-mm/spring-boot-jpa-docker)
* [Spring Boot JPA Docker Compose Sample](https://github.com/yewin-mm/spring-boot-jpa-docker-compose) (to get basic docker compose sample)

<a name="clone-project"></a>
### 🥡 Clone Project
* Clone the repo
   ```sh
   git clone https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline.git
   ```
  
<a name="prerequisites"></a>
### 🔑 Prerequisites
Prerequisites can be found here, [Spring Boot Application Instruction](https://github.com/yewin-mm/spring-boot-app-instruction). <br>
You need to install `Docker` in your machine. [Get Docker](https://www.docker.com/products/docker-desktop/). <br>
You need to install Jenkins Server in your machine. [Jenkins Server](https://github.com/yewin-mm/jenkins-server)
If you don't know about basic docker command, see here. [Spring Boot Docker Sample](https://github.com/yewin-mm/spring-boot-docker-sample)<br>
You can learn sample docker-compose commands here. [Spring Boot JPA Docker Compose Sample](https://github.com/yewin-mm/spring-boot-jpa-docker-compose). <br>

<a name="instruction"></a>
### 📝 Instruction
* Make sure Docker daemon and [Jenkins Server](https://github.com/yewin-mm/jenkins-server) is running in your machine.
* Call `docker ps` in your `CMD` or `terminal` to check docker daemon running or not.
* Call `http://localhost:9000/` in your browser to check server is alive or not.

<a name="check-properties-file"></a>
#### Check Properties file
* Run your application to make sure your application is running well in local.
* For that case, un-comment `spring.datasource.url` which connect to localhost db and comment out `spring.datasource.url` which using container db host in `application.properties` file.
* Here, if you **don't** run your MySQL db with container approach like this way [MySQL Container](https://github.com/yewin-mm/mysql-docker-container), <br>
  un-comment `spring.datasource.url` which connect to localhost db and comment out `spring.datasource.url` which using container db host in `application.properties` file.
* You need to check out `docker-compose.yml` file if you don't run your MySQL db as container.
* After successfully running in your local and if you use MySQL as container, comment out `spring.datasource.url` which connect to localhost db and un-comment `spring.datasource.url` which using container db host.
* Please note that, if you run MySQL db as container and gave the container name which not same with my [MySQL Container](https://github.com/yewin-mm/mysql-docker-container) approach, <br>
you need to add your container name in `application.properties` file and `docker-compose.yml` file.

<a name="check-jenkins-file"></a>
#### Check Jenkins file
* You need make sure Maven Prerequisites as per this tutorial. [Setting up maven in Jenkins Server](https://github.com/yewin-mm/jenkins-server#maven)
* You need to make sure Docker Prerequisites as per this tutorial. [Setting up docker in Jenkins Server](https://github.com/yewin-mm/jenkins-server#docker)
* Make sure below setting up list as per above tutorials,
1. Maven Plugins
2. Docker plugins
3. Maven Tools setup
4. Add Docker Credentials
5. Create Docker Repository

* After that, in `Jenkinsfile`,
* You need to change `repository`as per you created in Docker Hub.
* You need to change `userId` that id is your Docker Hub account Id.
* You need to check your setting docker Credentials id in Jenkins Server which need to be same with `DOCKER_HUB_CREDENTIALS` under `environment` section of `Jenkinsfile`.
* You need to check your installed Maven tools version in Jenkins Server which need to be same with `maven` field under `tools` section of `Jenkinsfile`.


<a name="push-github"></a>
#### Push to GitHub
* Create `Repository` in `Github`.
* Change git branch name and repository link under `stage('Clone Project')` in `Jenkinsfile`. Here, you can use `master` as branch name when you pushed to your Github.
* Push this project to your `Github` with above repository link. (you need to add your `git remote url` and push with `git push -u origin master`)

<a name="build-app"></a>
#### Build Application Through Jenkins
* There are many ways to build application through Jenkins.
* Here, I will use pipeline to build application to handle dynamic configuration.
* Using pipeline scripts, there are two ways to build application,
1. Using Pipeline in Portal
2. Using Multibranch pipeline
* Those two approach have different benefits. 
* If you run your application as sample you can use Pipeline and If you run your application with multiple branch, you can use Multibranch pipeline.
* If you use Multibranch pipeline, you need to remove branch in my `Jenkinsfile` when you clone from your Github repository.
* Here, I will use `#1` approach to build application with sample Pipeline way.

<a name="copy-script"></a>
##### Copy Pipeline script and Add in Jenkins
* Please make sure you already [Check Jenkinsfile](#check-jenkins-file") in above step. 
* Copy the whole script inside `Jenkinsfile`.
* Open Jenkins Server with `http://localhost:9000/`.
* Click `New Item` and give name to `spring-boot-jpa-docker-jenkins-pipeline`. ref - [New Pipeline Script Adding](https://github.com/yewin-mm/jenkins-server#testing)
* Under `Pipeline Script` Section, paste your pipeline script code from `Jenkinsfile`.
  <h3 align="center">
  Adding Pipeline Script Sample
  <img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/pipeline_script.png" /><br/>
  </h3>

* Click `Save`.

<a name="build"></a>
##### Build
* After you have entered that above `spring-boot-jpa-docker-jenkins-pipeline` pipeline job, 
* Click `Build Now` and refresh page.
* There you will see job is running with build number `#1`.
  <h3 align="center">
  Building Sample
  <img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/running.png" /><br/>
  </h3>

* Click `Console Output` which can be reach by clicking `down arrow` button beside `#1` job like above picture, to see console output.
  <h3 align="center">
  Console Output
  <img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/console.png" /><br/>
  </h3>

* There, you can see some console which you printed with `echo` in pipeline script.
* After that, go back to your Job and there, you can see Successful pipeline with all stages were green line.
  <h3 align="center">
  Successful pipeline
  <img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/success.png" /><br/>
  </h3>

<a name="check-error"></a>
##### Check Error
* Here, you may get some error like can't connect to `Docker Hub`, can't find repository, etc.
* You can see above console log to check what exactly error is.
* If you can't connect to `Docker Hub` with `permission denied` error,
  * Please make sure script is already running like this tutorial, [Run Script](https://github.com/yewin-mm/jenkins-server#run-script).
* If you got error like Can't Login to Docker Hub with wrong credentials,
  * Please make sure docker hub credentials is already setting up in your Jenkins server like this tutorial, [Setting up docker in Jenkins Server](https://github.com/yewin-mm/jenkins-server#docker).
  * Please make sure repository name in docker hub is same with repository name in `Jenkinsfile`.
* If you got error in Git Clone stage,
  * Please make sure you Github repository is existed and public access.
  * if it's private, you need to add Github credentials in Jenkins Server like above adding docker credentials <br>
   and uncomment git clone with credentials in `Jenkinsfile` which I already drop as sample.
* If you are error with Maven,
  * Please make sure maven setup is already in Jenkins server like this way, [Setting up maven in Jenkins Server](https://github.com/yewin-mm/jenkins-server#maven).
  * and check name in `Jenkinsfile` and name that you configure in Jenkins Server is same or not.
* Please make sure Maven Plugin and Docker plugin is already installed in Jenkins Server.
* Please make sure your Github Repository is already existed and code are in there and git clone link in `Jenkinsfile` is correct link to your repository.

<a name="check-dockerhub"></a>
##### Check Docker Hub
* You can use Docker Hub as the public repository cloud storage.
* So, you can freely pushed your image to docker hub.
* After you run successfully the whole pipeline,
* You can check your pushed image is in your Docker Hub Repository.
* Go to [Docker-Hub](https://hub.docker.com/)
* Login with your account and see under `Your created Repository` column.
* You can see your application image is already pushed from local to your Docker Hub.
* There, you can see image version is `0.0.1` or something like that format. 
* That is due to I added `automatic version` based on Jenkins Build Number in `Jenkins Pipeline Scripted`. 
* Version format is major.minor.patch (eg. 0.0.1) and when the patch version is reach to 999, the minor version was increased and patch version will be start from zero. (eg. 0.1.0) 
<h3 align="center">
Check image in Docker Hub 
<img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/docker_hub.png" /><br/>
</h3>


<a name="check-test-result"></a>
##### Check Test Result Chart
* You can check test result chart under your application pipeline job in you Jenkins Portal.
* Because I set up test classes and mapped with Surefire-Report, which can be read from Jenkins Server due to `surefire plugin` which you already installed when starting Jenkins server.
* So that Jenkins Server can show Test report as graph chart in relative Pipeline.
* Chart result can be seen after you have one successful and one failed in your pipeline.
* So, change docker hub userid in Jenkins credentials or change something in your test class and `push` to your Github repository.
* Change docker hub user id to get error in Jenkins Server when pushing image to docker hub is easy to get error. (if you change test code, you need to push code)  
* Please note that you don't need to create `new item` for next time in Jenkins server.
* Go inside your created pipeline job, and click `build now` button again in your pipeline.
* You will see failed in pipeline stage.
* After that you can see `Test Result Chart` in your pipeline job.
<h3 align="center">
Test Result Graph Chart
<img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/chart.png" /><br/>
</h3>

<a name="test-new-image"></a>
##### Test New Image Version in Docker Hub
* Change back to right userid or back to normal code after you test for failed case.
* Click `build now` again and you can see Jenkins build number is increase and image version will use that build number.
* After successfully running the whole pipeline,
* Go to [Docker-Hub](https://hub.docker.com/) again.
* Login with your account and see image under your created repository.
* There, you can see new image with different versions number.
  <h3 align="center">
  Check New Version Sample
  <img src="https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline/blob/master/github/template/images/overview/version.png" /><br/>
  </h3>


* Congratulations!!! you've successfully built application by using Jenkins Server and pipeline script.
* This example, I showed only pushed app image to Docker Hub. You can deploy and test the API as well.

***After that you can see the code, `Jenkinsfile`, `Dockerfile` and `docker-compose.yml` file and see the comment as well. You can learn it, and you can apply in your job or study fields.***

***Have Fun and Enjoy in Learning Code***


<a name="contact"></a>
## ✉️ Contact
Name - Ye Win <br> LinkedIn profile -  [Ye Win's LinkedIn](https://www.linkedin.com/in/ye-win-1a33a292/)  <br> Email Address - yewin.mmr@gmail.com

Project Link: [Spring Boot Jpa Docker Jenkins Pipeline](https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline)


<a name="contributing"></a>
## ⭐ Contributing
Contributions are what make the open source community such an amazing place to be learnt, inspire, and create. Any contributions you make are **greatly appreciated**.
<br>If you want to contribute....
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/yourname`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeatures'`)
4. Push to the Branch (`git push -u origin feature/yourname`)
5. Open a Pull Request
