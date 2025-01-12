#!/user/bin/env groovy

def call() {
    echo "building the application for branc $BRANCH_NAME"
    sh "mvn clean package"
}