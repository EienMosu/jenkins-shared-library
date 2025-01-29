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

//    def dockerLogin() {
//        script.withCredentials([script.usernamePassword(credentialsId: "dockerhub", passwordVariable: "PASS", usernameVariable: "USER")]) {
//            script.sh "echo '${script.PASS} | docker login -u '${script.USER} --password-stdin"
//        }
//    }

    def dockerLogin() {
        // Store credentials securely using environment variables
        def username = env.USER
        def password = sh(returnStdout: true, script: 'echo ${PASS}') // Capture password output

        // Login using process builder for more control
        def process = new ProcessBuilder(
                'docker', 'login',
                '-u', username,
                '-p', password.trim() // Trim whitespaces from password
        ).start()

        // Capture output and error streams
        def stdout = process.getInputStream().text
        def err = process.getErrorStream().text.trim() // Trim error stream

        // Check exit value for successful login (0)
        if (process.waitFor() == 0) {
            echo "Login to Docker Hub successful for user: ${username}"
        } else {
            echo "Login failed: ${err}"
        }
    }

    def dockerPush(imageName) {
        script.sh "docker push $imageName"
    }
}
