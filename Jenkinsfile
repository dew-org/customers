pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
             git branch: 'develop', url: 'https://github.com/dew-org/customers'
            }
        }
        stage('Gradle build') {

            steps {
                sh 'env | sort'
                sh './gradlew assemble --no-daemon --stacktrace'
            }
        }
    } // stages
} // pipeline