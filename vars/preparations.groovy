#!/usr/bin/env groovy

def call(body) {
    this.echo "Preparations stage..."
    this.stage("Preparations") {
    }
    return this
}
