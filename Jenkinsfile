/* declarative pipeline */

// declare variable as global (not declare in script tag) to use in other stages
// below variable is to store values from pom.xml
def readPom

// below variable is repository name which you need to define in your registry server. eg. docker hub. (ref - https://github.com/yewin-mm/jenkins-server#docker)
// here, it should be your repository name which you created in docker hub
def repository = "spring-boot-jpa-docker-jenkins-pipeline"

// docker hub user id (here, it should be your docker user id)
def userId = "yewin1"

// below variable is imageName which we will get from `artifactId` of `pom.xml` file which we will use in docker build and push
def imageName

// we will define tagName in below
def tagName

// we will set full path to push into docker hub in below checkout stage (registry url + repository + image name + tag version)
def dockerPushPackage

def docImage

pipeline {

  environment {

    // that is for docker hub registry and if you use your custom cloud server registry, you need to set your registry url
    REGISTRY = "docker.io"

    // that is we already pre-defined with name `docker-hub` in jenkins Dashboard -> Manage Jenkins -> Credentials (ref - https://github.com/yewin-mm/jenkins-server#docker)
    // here, it should be your created credentials id to access docker hub in Jenkins Server.
    DOCKER_HUB_CREDENTIALS = credentials('docker-hub')

    // we will use below AUTOMATIC_TAG as image tag when pushing to docker hub.
    // if jenkins build number is 1, automatic version number will start from 0.0.1 (major.minor.patch) version as it's use build number
    // if the automatic number is 0.0.999 and the next jenkins build number is come with 1000, the automatic version number will be 0.1.0,
    AUTOMATIC_TAG = sh(script: 'echo "$BUILD_NUMBER" | awk \'{printf "%d.%d.%d", $1 / 10000, $1 / 100 % 100, $1 % 100}\'', returnStdout: true).trim()

  }
  agent any

  // there are 3 ways to use maven with Jenkins
  // 1. you can use docker maven image as agent (this way is a bit hard than way 2, but you can use different maven version as you want and can use share volume storage which not to download dependency every time)
  // 2. you can use maven as tools and install in Jenkins (this way is easy and with this ways, you can only use specific maven version which you installed in Jenkins)
  // 3. you can connect to dockerfile which pull maven image from docker hub.

  // here, I used way 2 as that is easy way.
  // that is we already set up adding maven in jenkins Dashboard -> Manage Jenkins -> Global Tool Configuration (ref - https://github.com/yewin-mm/jenkins-server#maven)
  tools {
    // for using maven tools in jenkins, you need to add 3 plugins in Jenkins Server.
    // `Maven Integration`, `Pipeline Maven Integration` and `Pipeline Utility Steps` (to read pom.xml file)
    // add above plugins in Jenkins Portal (Manage Jenkins -> Manage Plugins -> Available Plugins -> search {above plugins} and install -> restart jenkins)

    // you need to add Global Tools Configuration in Jenkins Server too. (ref - https://github.com/yewin-mm/jenkins-server#maven)
    // go to Jenkins Portal (Manage Jenkins -> Global Tools Configuration -> Add maven -> give name with Capital Letter (Maven-3.9.0) -> Select version to 3.9.0 and save)
    maven 'Maven-3.9.0'
  }

  // parent stages to define stages
  stages {

    // you can create new pipeline in jenkins and can test these whole pipeline under pipeline scrips tab of new pipeline configure section
    // stage tag is to separate function in Jenkins Pipeline UI
    stage('Clone Project') {

      // set timeout
      options {
        timeout(time: 10, unit: "MINUTES")
      }
      // do action with steps tab
      steps {
        // print in pipeline job console with echo statement, if you want to print the variable values using echo, you need to do with `sh`
        echo "Start Clone Project process."

        // Below is getting code from a GitHub repository
        // if your project repository is private, you need credential (username, password) to access that repository.
        // for that case, you can pre-defined credentials in Jenkins Server
        // To set credentials -> go to Manage Jenkins -> Manage Credentials -> global -> Add credentials -> add your Domain username and pwd (eg. Github) and give ID (you can that id as you want)

        // you can test by auto generating Pipeline Script by going `Pipeline Syntax` in Configure section of Pipeline.
        // below is for github private repository (login with credentials id to connect your github account which you need to create in Jenkins Server)
//         git branch: 'master', credentialsId: 'github', url: 'https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline'

        // above `git branch` command is sample code to access private repository, but, here, my project is public repository and so, I don't need any credentials to login.
        // you can use your repository instead of below my repository, but your repository should have Jenkins Pipeline file like this file to run Jenkins Jobs.
        git branch: 'master', url: 'https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline'

        // you can use git clone command and checkout scmGit (install git plugin) instead of above git command
        // you need git checkout command if you don't want to specify the branch and run with Multibranch pipeline approach in Jenkins server.
        // but here, I do simpler way and don't use with multiple branch so, I go with git branch command.

        // we used script tag to create variable and call function
        script {

          // readMavenPom is a function which provided by `Pipeline Utility Steps` plugins which already added to Jenkins Server in above tools section.
          // set pom properties into readPom variable which we define in above
          readPom = readMavenPom file: 'pom.xml';

          // set value into imageName variable which we declare in above and that imageName will use in docker build image below
          imageName = "${readPom.artifactId}:${env.AUTOMATIC_TAG}";

          // prepare for docker image with full path to push into docker hub, that tagName will use before pushing into docker hub
          tagName = "${userId}/${repository}:${env.AUTOMATIC_TAG}";

          // prepare for pushing image into docker hub
          dockerPushPackage = "${env.REGISTRY}/${tagName}";

        }

        echo "Application is running with Jenkins Build Number #${env.BUILD_ID}"
        echo "Application Name: ${readPom.name}"
        echo "Application Version: ${readPom.version}"

        // you can get and test other fields in `pom.xml`
//        echo "The value of description is: ${readPom.description}"
//        echo "The version is: ${readPom.version}"

        echo "End Clone Project process."
      }

    }

    // you can remove below Test stages in Pipeline (Smoke Test, Unit Test and Integration Test) if you don't want to test.
    // but it's better adding Testing before push image to registry or deploy to server, to cover some fail state like API call error, DB connection error, etc...

    stage('Smoke Test') {

      // set timeout
      // this step will take a bit minutes if you run this app as first time because it will download all dependencies.
      // if you haven't good internet, you can increase time in below timeout option. But it should be finished within 30 minutes as my application hasn't many dependencies.
      // if not finished within your set time, the pipeline will stop due to timeout function.
      options {
        timeout(time: 30, unit: "MINUTES")
      }
      // do action with steps tab
      steps {
        echo "Start Smoke Test process."

        // you can test by using `mvn test` command for testing all test classes
        // but here, I want to separated test and so, test specific class name.
        sh 'mvn test -Dtest="SmokeTest"'

        echo "End Smoke process."
      }
      // post stage will execute after above steps stage
      post {
        // `always` keyword will do always and you can use `success` instead of `always`, it's mean post stage will execute only if above steps stage has no error.
        always {
          junit(
            // allow if test result was empty to cover error
            allowEmptyResults: true,

            // if you test with above mvn test, the test result report files will generate under `target/surefire-reports/` folder,
            // you can checkout those report file in jenkins container (/var/jenkins_home/workspace/{pipeline project name}/target/surefire-reports/)
            // Junit will generate test report file and those Test report file will start with `TEST-` follow by package name and class name and the extension is `.xml`.
            // here, you can use `TEST-*.xml` to read all TEST result report files if you run with `mvn clean test` instead of testing with specific class name.
            // but I need to read as separate test report file as per above `maven test specific class name`.
            // so, I added the specific TEST report file name like  TEST-*SmokeTest.xml (* - will be anything before SmokeTest. normally, it's package name before Test class name)
            // you can check those report files by `ls` in Jenkins container (go inside docker jenkins container with docker exec command, go to /var/jenkins_home/workspace/{jenkins_server_pipeline_name}/target/surefire-reports)

            // So, As per reading Test result report xml file,
            // Jenkins can show as `Test Result Trend` graph chart in Jenkins Portal UI because of Junit Plugin and which is already installed in Jenkins after you install Jenkins with choosing install Suggested plugins at Jenkins startup time.
            testResults: 'target/surefire-reports/TEST-*SmokeTest.xml'
          )
        }
      }

    }

    stage('Unit Test') {

      // set timeout
      options {
        timeout(time: 30, unit: "MINUTES")
      }
      // do action with steps tab
      steps {
        echo "Start Unit Test process."

        // you can test by using `mvn test` command for testing all test classes.
        // but here, I want to separated test and so, test specific class name.
        sh 'mvn test -Dtest="StudentApplicationUnitTest"'

        echo "End Unit process."
      }
      // post stage will execute after above steps stage
      post {
        always {
          junit(
            // allow if test result was empty to cover error
            allowEmptyResults: true,

            // if you test with above mvn test, the test result report files will generate under `target/surefire-reports/` folder,
            // you can checkout those report file in jenkins container (/var/jenkins_home/workspace/{pipeline project name}/target/surefire-reports/)
            // Junit will generate test report file and those Test report file will start with `TEST-` follow by package name and class name and the extension is `.xml`.
            // here, you can use `TEST-*.xml` to read all TEST result report files if you run with `mvn clean test` instead of testing with specific class name.
            // but I need to read as separate test report file as per above `maven test specific class name`.
            // so, I added the specific TEST report file name like  TEST-*StudentApplicationUnitTest.xml (* - will be anything before StudentApplicationUnitTest. normally, it's package name before Test class name)
            // you can check those report files by `ls` in Jenkins container (go inside docker jenkins container with docker exec command, go to /var/jenkins_home/workspace/{jenkins_server_pipeline_name}/target/surefire-reports)

            // So, As per reading Test result report xml file,
            // Jenkins can show as `Test Result Trend` graph chart in Jenkins Portal UI because of Junit Plugin and which is already installed in Jenkins after you install Jenkins with choosing install Suggested plugins at Jenkins startup time.
            testResults: 'target/surefire-reports/TEST-*StudentApplicationUnitTest.xml'
          )
        }
      }
    }

    stage('Integration Test') {

      // set timeout
      options {
        timeout(time: 30, unit: "MINUTES")
      }
      // do action with steps tab
      steps {
        echo "Start Integration Test process."

        // you can test by using `mvn test` command for testing all test classes.
        // but here, I want to separated test and so, test specific class name.
        sh 'mvn test -Dtest="StudentApplicationIntegrationTest"'

        echo "End Integration process."
      }
      // post stage will execute after above steps stage
      post {
        always {
          junit(
            // allow if test result was empty to cover error
            allowEmptyResults: true,

            // if you test with above mvn test, the test result report files will generate under `target/surefire-reports/` folder,
            // you can checkout those report file in jenkins container (/var/jenkins_home/workspace/{pipeline project name}/target/surefire-reports/)
            // Junit will generate test report file and those Test report file will start with `TEST-` follow by package name and class name and the extension is `.xml`.
            // here, you can use `TEST-*.xml` to read all TEST result report files if you run with `mvn clean test` instead of testing with specific class name.
            // but I need to read as separate test report file as per above `maven test specific class name`.
            // so, I added the specific TEST report file name like  TEST-*StudentApplicationIntegrationTest.xml (* - will be anything before StudentApplicationIntegrationTest. normally, it's package name before Test class name)
            // you can check those report files by `ls` in Jenkins container (go inside docker jenkins container with docker exec command, go to /var/jenkins_home/workspace/{jenkins_server_pipeline_name}/target/surefire-reports)

            // So, As per reading Test result report xml file,
            // Jenkins can show as `Test Result Trend` graph chart in Jenkins Portal UI because of Junit Plugin and which is already installed in Jenkins after you install Jenkins with choosing install Suggested plugins at Jenkins startup time.
            testResults: 'target/surefire-reports/TEST-*StudentApplicationIntegrationTest.xml'

          )
        }
      }
    }

    // you can create new pipeline in jenkins and can test these whole pipeline under pipeline scrips tab of new pipeline configure section
    // stage tag is to separate function
    // you can combine this generating jar file stage with below Build Image stage.
    stage('Generate Jar') {
      // set timeout
      options {
        timeout(time: 30, unit: "MINUTES")
      }
      steps {
        echo "Start Generate Jar process."

        // generate jar file and skip test as I need to test in this stage and it's already in above.
        sh 'mvn clean package -DskipTests'
        echo "End Generate Jar process."
      }

    }

    /* stage('Build Image') {
      options {
        timeout(time: 30, unit: "MINUTES")
      }

      steps {
        echo 'Start Build Image process'
        // below docker traditional build command is deprecated now, so, I comment out this stage
        sh 'docker build -t spring-boot-jpa-docker-jenkins-pipeline .'
        echo 'End Build Image process'
      }
    } */

    stage('Build Image') {
      steps {
        echo "Start Build Image process."

        echo "Image Name: ${imageName}"
        // here I injected imageName value into docker-compose file with `DOCKER_IMAGE_NAME` which we already declare as argument variable in compose file
        sh "DOCKER_IMAGE_NAME=${imageName} docker-compose build --no-cache"

        echo "End Build Image process."
      }
    }

    // you can combine below Login and Push stage into one stage.
    // you can even do push image with withCredentials() method which provided by docker pipeline plugins.

    stage('Login to Docker Hub') {
      options {
        timeout(time: 30, unit: "MINUTES")
      }
      steps {
        echo "Start Login to Docker Hub process."

        // login into docker hub with `DOCKER_HUB_CREDENTIALS` which we already defined in above `environment` tag by using Jenkins credentials method.
        // We need bind docker hub credentials to Jenkins Server and here, we already defined docker credentials in Jenkins Server (ref - https://github.com/yewin-mm/jenkins-server#docker)
        sh 'echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin'

        echo 'Login Success'
        echo "End Login to Docker Hub process."
      }
    }

    stage('Push Image') {
      options {
        // sometime, push will take too long depend on your internet connection.
        timeout(time: 45, unit: "MINUTES")
      }
      steps {
        echo "Start Push Image process."

        // we need tag to image before pushing into docker hub.
        echo "Image Name: ${imageName}, Tag Name: ${tagName}"
        sh "docker tag ${imageName} ${tagName}"

        // get full path from above variable which we prepare in checkout stage and push that to docker hub.
        echo "Docker Push Full Package: ${dockerPushPackage}"
        sh "docker push ${dockerPushPackage}"

        echo "End Push Image process."
      }

      post {
        always {
          // remove local docker images after pushing. Because it's not necessary after pushing
          // if you don't do below removing image steps, it will take size and size will bigger every time your run the pipeline in your disk as you create image in above steps.
          // here, I do with post `always` action, because whatever you can pushed image to docker hub or not, the image will always create after above `Build Image` stage.

          // remove tag before removing image
          sh "docker rmi ${tagName}"
          sh "echo Successfully removed tag"

          // remove image
          sh "docker rmi ${imageName}"
          sh "echo Successfully removed image"

          // do logout whatever can push into docker hub or can't push into docker hub because before this stage, it was already login in above docker login stage.
          sh 'docker logout'
          sh "echo Successfully logged out"
        }
      }
    }

    // you can add post to catch `failure` like post { failure ... } or to catch `success` like post { success ... }
    // and can send as email notification to you and your team.

    // you can deploy (run) to your local tomcat server or run with docker container.

    // you can also pushed image to your private cloud registry server and you can even deploy this application to Cloud server if you have Cloud server eg. AWS EC2.

    // You can add more stages to separate deploy environment like `developer`, `qa`, `staging`, `production`, etc..
    // So, Jenkins can automatic building, pushing, deploying your application with single click by using powerful pipeline.
  }
}