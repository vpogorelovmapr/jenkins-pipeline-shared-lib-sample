#!/usr/bin/env groovy

def call(body) {
    this.echo "Build Docker image stage..."
    this.stage("Build Docker image") {
        this.sh "echo 'Execute your desired bash command here'"

    }
    return this
}
