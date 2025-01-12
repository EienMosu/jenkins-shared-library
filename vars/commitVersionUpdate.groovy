#!/user/bin/env groovy

def call() {
    withCredentials([usernamePassword(credentialsId: "github-username", passwordVariable: "PASS", usernameVariable: "USER")]) {
        sh "git remote set-url origin https://${USER}:${PASS}@https://github.com/EienMosu/java-maven-app-nana.git"
        sh "git add *"
        sh "git commit -m 'jenkins version bump'"
        sh "git push origin HEAD:main"
    }
}
