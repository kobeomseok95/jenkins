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

        stage('Upload Deploy Files to S3') {
            steps {
                sh 'mkdir deploy'
                sh 'cp scripts/*.sh ./deploy'
                sh 'cp appspec.yml ./deploy'
                sh 'cd deploy'
                sh 'pwd'
                sh 'tar -cvf deploy.tar *'
                withAWS(credentials: 'AWS IAM', region: 'ap-northeast-2') {
                    s3Upload(bucket: 'example-instance-init', file: 'deploy.tar', path: 'deploy/')
                }
                sh 'cd ..'
                sh 'pwd'
            }
        }

//         stage('Deploy') {
//             steps {
//                 withAWS(credentials: 'AWS IAM', region: 'ap-northeast-2') {
//                     createDeployment(
//                         applicationName: 'example-codedeploy',
//                         deploymentGroupName: 'example-deploy-group',
//                         s3Bucket: 'example-instance-init/deploy',
//                         s3BundleType: 'tar',
//                         s3Key: 'deploy.tar'
//                     )
//                 }
//             }
//         }
    }

    post {
         always {
            sh 'rm -rf deploy'
            sh 'docker rmi ${registry}'
            sh 'docker rmi openjdk:11-jdk-slim'
         }
    }
}
