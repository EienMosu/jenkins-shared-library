#!/user/bin/env groovy

def call() {
    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_APP_TOKEN')]) {
        sh 'git config --global user.email "jenkins@example.com"'
        sh 'git config --global user.name "jenkins"'

        sh "git status"
        sh "git branch"
        sh "git config --list"

        echo "------------------------------------------------------------------------------------------"

        sh "git remote set-url origin https://${GITHUB_APP_TOKEN}@github.com/EienMosu/java-maven-app-nana.git"
        sh "git add *"
        sh 'git commit -m "jenkins version bump"'
        sh "git push origin HEAD:main"
    }
}
