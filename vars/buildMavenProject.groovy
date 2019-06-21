#!/usr/bin/env groovy

def call(body) {
    this.echo "Build project stage..."
    this.stage("Build project") {
        this.echo "Build project started"
        this.sh "mvn clean package"
        this.echo "Build project ended"
    }
    return this
}
