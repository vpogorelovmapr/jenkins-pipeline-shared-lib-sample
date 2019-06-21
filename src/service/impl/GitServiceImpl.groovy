package service.impl

import service.GitFlowService
import service.GitService

class GitServiceImpl implements GitService {

    private static final String DEFAULT_CHECKOUT_FOLDER = "repo"
    private static String folder = DEFAULT_CHECKOUT_FOLDER

    String credentialsId
    String repo
    String repoOwner
    GitFlowService gitFlowService
    Script script = script

    GitServiceImpl(Script script, String repo, String credentialsId,
                   String repoOwner) {
        this.script = script
        this.repo = repo
        this.credentialsId = credentialsId
        this.repoOwner = repoOwner
        gitFlowService = new GitFlowService(this)
    }

    @Override
    def setUp() {
        script.echo "setUp git started"
        script.sh 'git config --global user.email "jenkins@javatar.pro"'
        script.sh 'git config --global user.name "jenkins"'
        script.echo "setUp git finished"
    }

    @Override
    def checkout(String branch) {
        script.echo "Git checkout repo: ${repo}, branch: ${branch}"
        script.echo "credentialsId: ${credentialsId}, url: ${repo}"
        script.git credentialsId: credentialsId, url: repo
        script.sh "pwd; ls -la"
        script.sh "git checkout ${branch}"
        script.echo "GitServiceImpl checkout successfully finished for repo: ${repo}, branch: ${branch}"
    }

    @Override
    def checkoutRepo(String repoOwner, String repo, String branch) {
        script.echo "Git checkoutRepo: repoOwner: ${repoOwner}, repo: ${repo}, branch: ${branch}"
        String repoUrl = urlResolver.getRepoUrl(repoOwner, repo)
        return checkoutRepo(repoUrl, branch)
    }

    @Override
    def checkoutRepo(String repoUrl, String branch) {
        script.timeout(time: 5, unit: 'MINUTES') {
            script.git credentialsId: credentialsId, url: repoUrl
            script.sh "pwd; ls -la"
            script.sh "git checkout ${branch}"
            script.echo "GitServiceImpl#checkoutRepo successfully finished for repoUrl: ${repoUrl}, branch: ${branch}"
        }
    }

    @Override
    def cleanUp() {
        script.echo "start cleanup"
        script.sh "pwd; ls -la"
        script.sh "rm -rf ${folder}"
        script.sh "rm -rf ${folder}@tmp"
        script.sh "mkdir ${folder}"
        script.sh "pwd; ls -la"
        script.echo "end cleanup"
    }

    def createReleaseBranchLocally(String releaseVersion) {
        script.echo "createReleaseBranchLocally releaseVersion - ${releaseVersion}"
        script.sh "git status"
        script.sh "git flow release start ${releaseVersion}"
        script.echo "createReleaseBranchLocally finished"
    }

    def commitChanges(String message) {
        script.echo "commit changes with message: ${message}"
        script.sh "git status"
        script.sh "git add ."
        script.sh "git status"
        script.sh "git commit -m \'${message}\'"
        script.echo "successfully committed"
    }

    def switchToBranch(String branch) {
        script.echo "switchToBranch: ${branch} started"
        script.sh "git checkout ${branch}"
        script.echo "switchToBranch: ${branch} finished"
    }

    def createBranch(String branchName) {
        script.sh "git checkout -b ${branchName}"
        commitChanges("create new branch: ${branchName}")
    }

    def createAndPushBranch(String branchName) {
        script.echo "started createAndPushBranch: ${branchName}"
        script.sh "git checkout -b ${branchName}"
        script.sshagent([credentialsId]) {
            script.sh "git push -u origin ${branchName}"
        }
        this.echo "finished createAndPushBranch: ${branchName}"
    }

    def pushNewBranches() {
        script.echo "started pushNewBranches"
        script.sshagent([credentialsId]) {
            script.sh "git push --all -u"
        }
        script.echo "finished pushNewBranches"
    }

    def pushNewBranch(String branchName) {
        script.echo "started pushNewBranch: ${branchName}"
        script.sshagent([credentialsId]) {
            script.sh "git push -u origin ${branchName}"
        }
        script.echo "finished pushNewBranch: ${branchName}"
    }

    String getProdBranch() {
        return BRANCH_MASTER
    }

    String getRepoName() {
        return repo
    }

    String getRepoOwner() {
        return repoOwner
    }

    def pushRelease() {
        script.sshagent([credentialsId]) {
            script.sh "git push"
            script.sh "git push --all"
            script.sh "git push origin --tags"
        }
    }

    List<String> getActiveBranches() {
        String output = pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh returnStdout: true, script: 'git branch -a'
        Set<String> result = new HashSet<>()
        output.split().findAll { it -> (!it.contains("*")
                    && !it.contains("HEAD") && !it.contains("->"))}
            .each{it -> result.add(it.replace("origin/", "")
                    .replace("remotes/", ""))}
        return new ArrayList<>(result)
    }

    List<String> getTags() {
        String output = pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh returnStdout: true, script: 'git tag'
        return output.split().toList()
    }

    String getCurrentBranch() {
        String output = pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD'
        return output.trim()
    }

    GitServiceImpl withUserName(String userName) {
        this.userName = userName
        return this
    }

    @Override
    def setUpVcsFlowPreparations() {
        gitFlowService.initFlow()
    }

    def getShowConfigFile() {
        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "cat .git/config"
    }

    public String toString() {
        return "GitServiceImpl{" +
                "credentialsId='" + credentialsId + '\'' +
                ", repo='" + repo + '\'' +
                ", repoOwner='" + repoOwner + '\'' +
                ", userName='" + userName + '\'' +
                "} " + super.toString();
    }
}
