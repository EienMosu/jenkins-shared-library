#!/user/bin/env groovy
package com.example

class Docker implements Serializable {
    def script

    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.echo "Building the docker image..."
        script.sh "docker build -t $imageName ."
    }

    def dockerLogin() {
        script.withCredentials([script.usernamePassword(credentialsId: "dockerhub", passwordVariable: "PASS", usernameVariable: "USER")]) {
            script.sh "echo '${script.PASS} | docker login -u '${script.USER} --password-stdin"
            script.sh "echo '$script.PASS}"
        }
    }

    def dockerPush(imageName) {
        script.sh "docker push $imageName"
    }
}
