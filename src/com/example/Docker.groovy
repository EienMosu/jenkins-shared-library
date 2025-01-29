#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {
    def script

    Docker(script) {
        this.script = script
    }

    // Utility method for executing shell commands
    def executeCommand(String command) {
        try {
            script.echo "Executing command: ${command}"
            def result = script.sh(script: command, returnStdout: true).trim()
            script.echo "Command output: ${result}"
            return result
        } catch (Exception e) {
            script.error "Error executing command: ${command}. Error: ${e.message}"
            throw e
        }
    }

    // Build Docker image
    def buildDockerImage(String imageName) {
        script.echo "Building Docker image: ${imageName}..."
        executeCommand("docker build -t ${imageName} .")
    }

    // Docker login using environment variables for credentials
    def dockerLogin() {
        def username = System.getenv("USER")
        def password = System.getenv("PASS")

        // Check if username and password are available
        if (!username || !password) {
            script.error "Docker login failed: Missing USER or PASS environment variables."
            return
        }

        script.echo "Logging in to Docker Hub using username: ${username}"

        // Log the password (not recommended in production)
        script.echo "Docker password is: ${password}"

        // Execute Docker login using ProcessBuilder for better control
        def loginCommand = "docker login -u ${username} -p ${password.trim()}"
        def result = executeCommand(loginCommand)

        if (result.contains("Login Succeeded")) {
            script.echo "Docker login successful for user: ${username}"
        } else {
            script.error "Docker login failed for user: ${username}. Result: ${result}"
        }
    }

    // Push Docker image to the registry
    def dockerPush(String imageName) {
        script.echo "Pushing Docker image: ${imageName}..."
        executeCommand("docker push ${imageName}")
    }
}