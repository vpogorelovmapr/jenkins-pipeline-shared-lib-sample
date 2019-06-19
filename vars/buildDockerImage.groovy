#!/usr/bin/env groovy

def call(body) {
    this.echo "Build Docker image stage..."
    this.stage("Build Docker image") {
        new Deployer(script:this).run()
    }
    return this
}
