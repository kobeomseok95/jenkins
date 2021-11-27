node {
    stage("checkout SCM") {
        checkout([
            $class: 'GitSCM',
            branches: [[name: 'main']],
            userRemoteConfigs: [[
                credentialsId: 'jenkins-github',
                url: 'https://github.com/kobeomseok95/jenkins.git'
            ]]
        ])
        echo "Build complete..."
    }

    stage("Stage 2") {
        echo "hello. this is stage 2..."
    }
}