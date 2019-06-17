#!/usr/bin/env groovy

def call(body) {
    this.echo "Integration test stage..."
    this.stage("Integration test") {
    }
    return this
}
