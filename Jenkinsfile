pipeline {

    agent any

    environment {
        dockerImage = ''
        registry = 'kobeomseok95/springbootapp'
        registryCredential == 'jenkins-docker-hub-credentials'
    }

    stages {
        stage('Build Docker image') {
            steps {
                script {
                    dockerImage = docker.build registry
                }
            }
        }

        stage('Uploading Image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}
