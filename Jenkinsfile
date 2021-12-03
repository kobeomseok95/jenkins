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
                withAWS(credentials: 'AWS IAM', endpointUrl: 's3://example-instance-init', region: 'ap-northeast-2') {
                    s3Upload(bucket: 'example-instance-init', file: 'scripts/deploy.sh', path: '/deploy/')
                    s3Upload(bucket: 'example-instance-init', file: 'appspec.yml', path: '/deploy/')
                }
            }
        }

//         stage('Deploy') {
//             steps {
//                 script {
//                     sh'''aws deploy create-deployment \
//                     --application-name example-codedeploy \
//                     --deployment-group-name example-deploy-group \
//                     --revision revisionType=S3,s3Location={bucket=example-instance-init,\
//                     key=appspec.yml,bundleType=YAML}
//                     '''
//                 }
//             }
//         }

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
