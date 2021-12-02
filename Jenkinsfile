pipeline {
    agent any

    environment {
        dockerImage = ''
        def registry = 'kobeomseok95/springbootapp'
        registryCredential = 'jenkins-docker-hub-credentials'
        awsCredential = 'aws-credential'
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
                step([$class: 'AWSCodeDeployPublisher',
                                applicationName: 'example-codedeploy',
                                credentials: 'aws-credential',
                                deploymentGroupName: 'example-deploy-group',
                                deploymentMethod: 'deploy',
                                region: 'ap-northeast-2',
                                s3bucket: 'example-instance-init',
                                waitForCompletion: false])
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
