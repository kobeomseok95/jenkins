pipeline {
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                credentialsId: 'jenkins-github',
                url: 'https://github.com/kobeomseok95/jenkins.git'
            }
        }
    }
}