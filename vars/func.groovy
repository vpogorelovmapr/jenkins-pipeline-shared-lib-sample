#!/usr/bin/env groovy

def call(body) {
    this.echo "Triggering stage..."
    this.stage("Stage name") {
        script.echo "Triggering in stage..."
    }
    return this
}
