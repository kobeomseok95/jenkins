pipeline {
    agent any

    environment {
        dockerImage = ''
        def registry = 'kobeomseok95/springbootapp'
        registryCredential = 'dockerhub-credential'
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

        // copy and upload s3
        stage('Upload Deploy Files to S3') {
            steps {
                withAWS(credentials: 'AWS IAM', region: 'ap-northeast-2') {
                    s3Upload(bucket: 'example-instance-init', file: 'scripts/deploy.sh', path: 'deploy/')
                    s3Upload(bucket: 'example-instance-init', file: 'appspec.yml', path: 'deploy/')
                }
            }
        }

        stage('Deploy') {
            steps {
                withAWS(credentials: 'AWS IAM', region: 'ap-northeast-2') {
                    createDeployment(
                        applicationName: 'example-codedeploy',
                        deploymentGroupName: 'example-deploy-group',
                        s3Bucket: 'example-instance-init',
                        s3BundleType: 'YAML',
                        s3Key: 'appspec.yml'
                    )
                }
            }
        }

        stage('Delete Image') {
            steps {
                script {
                    sh 'docker rmi ${registry}'
                    sh 'docker rmi openjdk:11-jdk-slim'
                }
            }
        }
    }
}
