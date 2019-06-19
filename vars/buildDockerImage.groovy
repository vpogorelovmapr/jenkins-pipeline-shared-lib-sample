#!/usr/bin/env groovy

import pipeline.*

def call(body) {
    this.echo "Build Docker image stage..."
    this.stage("Build Docker image") {
        new Deployer(script:this).run()
        new Sample(3, 4)
    }
    return this
}
