#!/usr/bin/env groovy

def call(body) {
    this.echo "Triggering ${name} stage..."
    this.stage(name) {
        script.echo "Triggering in ${name} stage..."
    }
    return this
}
