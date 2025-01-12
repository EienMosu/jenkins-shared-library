#!/user/bin/env groovy

def call() {
    withCredentials([string(credentialsId: 'jenkins-github', variable: 'GITHUB_APP_TOKEN')]) {
        sh '''
                        echo "Authenticating with GitHub App..."
                        git add *
                        git commit -m "version update"
                        git push origin main
                        '''
    }
}
