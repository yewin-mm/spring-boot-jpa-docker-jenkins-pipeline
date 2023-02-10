# you can find basic sample dockerfile  as below link
# https://github.com/yewin-mm/spring-boot-docker-sample#instruction


#------------------------------------------------------------------
### Below is to automatic generate jar file with docker, so that you don't need to generate jar file, you just need docker build and docker run commands.
### Here, you don't need to generate jar file as Jenkin pipeline will generate jar file.
#------------------------------------------------------------------

# there are two ways to generate jar file by using maven.
# 1. using maven image,
# 2. copying .mvn folder

## is comment out for definition
# is code that you can uncomment and can test it without manual typing `mvn clean package` in your CMD or terminal

##1.
## pull maven image from DockerHub to run mvn command and give that name as builder to use in next step, you can give any name you want (here, I used multi-stage build which is better way using docker)
#FROM maven:3.8.7-eclipse-temurin-11-alpine as builder
## create folder (if not existed) and define working folder inside container
#WORKDIR /opt/app
## copy our project into that directory
#COPY . /opt/app
## generate jar file by using maven which we already add maven image in above, (you can use `mvn clean install -DskipTests`) (this step will take a few minutes)
#RUN mvn clean package -DskipTests

## you can find more about maven image tag versions in here, https://github.com/docker-library/docs/tree/master/maven

##2.
## pull jdk for java which to run maven command and give that name as builder to use in next step, you can give any name you want (here, I used multi-stage build which is better way using docker)
#FROM eclipse-temurin:11-jdk-alpine as builder
## create folder (if not existed) and define working folder inside container
#WORKDIR /opt/app
## copy .mvn folder into that directory
#COPY .mvn/ .mvn
## copy pom.xml file into that directory
#COPY mvnw pom.xml ./
## run to generate dependencies and copy offline downloaded dependencies into that directory (this step will take a few minutes)
#RUN ./mvnw dependency:go-offline
## copy src folder into that directory
#COPY ./src ./src
## generate jar file by using .mvnw (you can use `./mvnw clean install -DskipTests`) (as this way, we don't need to download (maven image) like above #1 way because we copied .mvn folder in above) (this step will take a few minutes)
#RUN ./mvnw clean package -DskipTests


#------------------------------------------------------------------
### Above is to generate jar file with docker.
### Here, you don't need to generate jar file as Jenkin pipeline will generate jar file.
#------------------------------------------------------------------

#FROM openjdk:8-jdk-alpine (old way)
# use eclipse temurin instead of above openJDK for better performance and small size
# eclipse temurin can provide java as well
# use JRE instead of JDK for small size and JRE is enough to run jar.
FROM eclipse-temurin:11-jre-alpine
# below is for java 8
#FROM eclipse-temurin:8-jre-alpine
# you can find more about temurin tag versions in here, https://github.com/docker-library/docs/blob/master/eclipse-temurin/README.md#supported-tags-and-respective-dockerfile-links
# alpine image tag is a light weight image than other image tags

# set working directory
WORKDIR /opt/app

## you can define static jar file name without snapshot version with `<finalName>` tag inside `<build>` tag in `pom.xml` file.
COPY target/spring-boot-jpa-docker-jenkins-pipeline.jar  app.jar

# below copy command is use when you genereate jar file with maven in docker container.
# from `builder` is from above step which we gave the name to `builder`
# opt/app is the path where we generate jar file in above step
#COPY --from=builder /opt/app/target/spring-boot-jenkins-pipeline-docker.jar  app.jar

ENTRYPOINT ["java","-jar","app.jar"]
