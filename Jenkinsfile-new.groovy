pipeline {

    agent any

    // there are 3 ways to use maven with Jenkins
    // 1. you can use docker maven image as agent (this way is a bit hard than way 2, but you can use different maven version as you want and can use share volume storage which not to download dependency every time)
    // 2. you can use maven as tools and install in Jenkins (this way is easy and with this ways, you can only use specific maven version which you installed in Jenkins)
    // 3. you can connect to dockerfile which pull maven image from docker hub.

    // here, I used way 2 as that is easy.
    tools {
        // for using maven tools, you need to add 3 plugins in Jenkins Server.
        // `Maven Integration`, `Pipeline Maven Integration` and `Pipeline Utility Steps`
        // add above plugins in Jenkins Portal (Manage Jenkins -> Manage Plugins -> Available Plugins -> search {above plugins} and install -> restart jenkins)
        // you need to add Global Tools Configuration in Jenkins Server.
        // go to Jenkins Portal (Manage Jenkins -> Global Tools Configuration -> Add maven -> give name with Capital Letter (Maven-3.8.7) -> Select version to 3.8.7 and save)
        maven 'Maven-3.8.7'
    }

    stages {

        // test maven version, you can create new pipeline and can test these whole pipeline under pipeline scrips tab of new pipeline configure section
        /*stage('Docker maven test') {
            steps {
                sh 'mvn --version'
            }
        }*/

        // you can create new pipeline and can test these whole pipeline under pipeline scrips tab of new pipeline configure section
        // stage tag is to separate function
        stage('Checkout') {
            echo "Start Checkout process."
            // do action with steps tab
            steps {
                echo "Application is running with Jenkins Build Number #${env.BUILD_ID}"
                // Get some code from a GitHub repository
                // if your project repository is private, you need credential (username, password) to access that repository.
                // for that case, you can pre-defined credentials in Jenkins Server
                // To set credentials -> go to Manage Jenkins -> Manage Credentials -> global -> Add credentials -> add your Domain username and pwd (eg. Github) and give ID (you can that id as you want)
                // you can test by auto generating Pipeline Script by going Pipeline Syntax in Configure section of Pipeline.
//                git branch: 'master', credentialsId: 'github', url: 'https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline'
                // above is sample code to access private repository, but, here, my project is public repository and so, I don't need credentials.
                git branch: 'master', url: 'https://github.com/yewin-mm/spring-boot-jpa-docker-jenkins-pipeline'

            }
            echo "End Checkout process."
        }

        // you can create new pipeline and can test these whole pipeline under pipeline scrips tab of new pipeline configure section
        // stage tag is to separate function
        stage('Build') {
            echo "Start Build process."
            steps {
                // we used script tag to create variable and call function
                script {
                    // readMavenPom is a function which provided by Pipeline Utility Steps plugins which already add to Jenkins Server in above tools section.
                    def readXmlFile = readMavenPom file: 'pom.xml';
                    def name = readXmlFile.name; // add name value in name variable
                    echo "Application Name: ${name}"
                    // you can test below fields by printing
//                    echo "The value of description is: ${readXmlFile.description}"
//                    echo "The version is: ${readXmlFile.parent.version}"
                }
            }
            // do action with steps tab
            steps {
                sh 'mvn clean package -DskipTests'
                echo "Successfully generate Jar file"
            }
            echo "End Build process."
        }

    }

}