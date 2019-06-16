#!/usr/bin/env groovy

def call(body) {
    this.echo "Triggering stage..."
    this.stage("Stage name") {
        this.echo "Triggering in stage..."
    }
    return this
}
