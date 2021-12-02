pipeline {
    agent any

    environment {
        dockerImage = ''
        def registry = 'kobeomseok95/springbootapp'
        registryCredential = 'jenkins-docker-hub-credentials'
    }

    stages {
        stage('Build Source') {
            steps {
                sh './gradlew clean build -x test'
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
                    sh '''aws deploy create-deployment \
                        --application-name example-codedeploy \
                        --deployment-group-name example-deploy-group \
                        --s3-location bucket=example-instance-init,key=appspec.yaml,bundleType=YAML \
                        --region ap-northeast-2'''
                }
            }
        }

        stage('Delete Image') {
            steps {
                script {
                    sh 'docker rmi ${registry}'
                }
            }
        }
    }
}
