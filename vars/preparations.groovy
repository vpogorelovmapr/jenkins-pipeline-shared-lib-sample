#!/usr/bin/env groovy
import service.GitService

def call(body) {
    this.echo "Preparations stage..."
    this.stage("Preparations") {
        this.echo  "${params}"
        GitService gitService = new GitService(this, "https://github.com/vpogorelovmapr/jenkins-pipeline-shared-lib-sample",
        "33f26755-b867-45ac-b647-3e22e1903437", "vpogorelovmapr")
        gitService.cleanUp()
    }
    return this
}
