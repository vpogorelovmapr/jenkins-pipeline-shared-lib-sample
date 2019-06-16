#!/usr/bin/env groovy

def call(body) {
    this.echo "Triggering stage..."
    this.stage("Stage name2") {
        this.echo "Triggering in stage..."
    }
    return this
}
