#!/usr/bin/env groovy

def call(body) {
    this.echo "Triggering ${name} stage..."
    script.stage(name) {
        script.echo "Triggering in ${name} stage..."
    }
    return this
}
