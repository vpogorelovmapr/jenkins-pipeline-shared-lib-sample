#!/usr/bin/env groovy

import pipeline.Deployer

def call(body) {
    this.echo "Build Docker image stage..."
    this.stage("Build Docker image") {
        new Sample(1, 2)
    }
    return this
}
