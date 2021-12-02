pipeline {
    agent any

    environment {
        dockerImage = ''
        registry = 'kobeomseok95/springbootapp'
        registryCredential = 'jenkins-docker-hub-credentials'
    }

    stages {
        stage('Build Source') {
            steps {
                sh './gradlew clean build'
            }
        }

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

        stage('Deploy') {
            steps {
                script {
                    sh 'chmod +x scripts/deploy.sh'
                    sh './scripts/deploy.sh'
                }
            }
        }

        stage('Delete Image') {
            steps {
                script {
                    sh 'docker rmi &{registry}'
                }
            }
        }
    }
}
