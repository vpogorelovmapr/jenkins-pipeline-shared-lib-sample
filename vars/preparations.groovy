#!/usr/bin/env groovy

import service.impl.GitServiceImpl

def call(body) {
    this.echo "Preparations stage..."
    this.stage("Preparations") {
        this.echo  "${params}"
        GitServiceImpl gitService = new GitServiceImpl(this, "https://github.com/vpogorelovmapr/websocket-lobby-service",
        "33f26755-b867-45ac-b647-3e22e1903437", "vpogorelovmapr")
        gitService.cleanUp()
        gitService.checkout("develop")
    }
    return this
}
