#!/usr/bin/env groovy

def call(body) {
    this.echo "Preparations stage..."
    this.stage("Preparations") {
        this.echo "Print ${envs}"
    }
    return this
}
