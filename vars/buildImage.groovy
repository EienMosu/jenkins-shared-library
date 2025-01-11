#!/user/bin/env groovy

def call() {
    echo "Building the docker image..."
    withCredentials([usernamePassword(credentialsId: "dockerhub",passwordVariable: "PASS", usernameVariable: "USER")]) {
        sh "docker build -t ozkan1234/demo-app:jma-2.0 ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push ozkan1234/demo-app:jma-2.0"
    }
}