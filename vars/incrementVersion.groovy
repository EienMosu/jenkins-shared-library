#!/user/bin/env groovy

def call() {
    echo "incrementing app version..."
    sh "mvn build-helper:parse-version versions:set \
-DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
versions:commit"
}
