pipeline {
    agent any

    environment {
        dockerImage = ''
        def registry = 'kobeomseok95/springbootapp'
        registryCredential = 'jenkins-docker-hub-credentials'
        def awsCredential = 'aws-credential'
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

//         stage('Copy Deploy Files') {
//             steps {
//                 script {
//                     sh 'mkdir deploy'
//                     sh 'cp appspec.yml deploy/'
//                     sh 'scripts/*.sh deploy/'
//                 }
//             }
//         }

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
                }
            }
        }
    }
}
