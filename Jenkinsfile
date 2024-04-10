pipeline {
    environment {
        imageName = "nexus:8082/repository/my-local-docker-repo/congratulation-manager-bot:$BUILD_NUMBER"
        registryCredentials = "nexus-jenkins-docker"
        registry = "https://nexus:8082"
        dockerImage = ''
    }
    agent any
    stages {
        stage('Git checkout'){
            steps{
                git credentialsId: '4851be6a-eea6-40b6-9004-53706128ca31', url: 'http://pechatny.synology.me:10080/d.pechatnikov/congratulation-manager-bot.git'
            }
        }
        stage('Build image'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'VAULT_TOKEN', variable: 'VAULT_TOKEN')]) {
                        dockerImage = docker.build(imageName, "--build-arg VAULT_TOKEN=${VAULT_TOKEN} .")
                    }
                }
            }
        }
        stage('Upload image to Nexus') {
            steps {
                script {
                    withDockerRegistry(credentialsId: registryCredentials, url: registry) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Docker Run') {
            steps {
                script {
                    sh 'docker stop congratulation-manager-bot || true'
                    sh 'docker run -d --rm -p 8080:8080 --name congratulation-manager-bot -e ENVIRONMENT_PROFILE_NAME=\'prod\' ' + imageName
                }
            }
        }
    }
}
