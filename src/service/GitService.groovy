package service

interface GitService {

    abstract def setUp()

    abstract def setUpVcsFlowPreparations()

    abstract def checkout(String branch)

    abstract def checkoutRepo(String repoOwner, String repo, String branch)

    abstract def checkoutRepo(String repoUrl, String branch)

    abstract def cleanUp()
}
