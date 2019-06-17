#!/usr/bin/env groovy

def call(body) {
    this.echo "Deploy to env stage..."
    this.stage("Deploy to env") {
    }
    return this
}
