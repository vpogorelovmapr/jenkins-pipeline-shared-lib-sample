package service

class GitService extends RevisionControlService {

    String credentialsId
    String repo
    String repoOwner
    GitFlowService gitFlowService

    GitService(Script script, String repo, String credentialsId,
               String repoOwner) {
        this.script = script
        this.repo = repo
        this.credentialsId = credentialsId
        this.repoOwner = repoOwner
        gitFlowService = new GitFlowService(this)
    }

    @Override
    def setUp() {
        this.echo "setUp git started"
        this.sh 'git config --global user.email "jenkins@javatar.pro"'
        this.sh 'git config --global user.name "jenkins"'
        this.echo "setUp git finished"
    }

    @Override
    def checkout(String branch) {
        this.echo "Git checkout repo: ${repo}, branch: ${branch}"
//        this.String repoUrl = urlResolver.getRepoUrl()
        this.echo "credentialsId: ${credentialsId}, url: ${repo}"
        this.git credentialsId: credentialsId, url: repo
        this.sh "pwd; ls -la"
        this.sh "git checkout ${branch}"
        this.echo "GitService checkout successfully finished for repo: ${repo}, branch: ${branch}"
    }

    @Override
    def checkoutRepo(String repoOwner, String repo, String branch) {
        this.echo "Git checkoutRepo: repoOwner: ${repoOwner}, repo: ${repo}, branch: ${branch}"
        String repoUrl = urlResolver.getRepoUrl(repoOwner, repo)
        return checkoutRepo(repoUrl, branch)
    }

    @Override
    def checkoutRepo(String repoUrl, String branch) {
        this.timeout(time: 5, unit: 'MINUTES') {
            this.git credentialsId: credentialsId, url: repoUrl
            this.sh "pwd; ls -la"
            this.sh "git checkout ${branch}"
            this.echo "GitService#checkoutRepo successfully finished for repoUrl: ${repoUrl}, branch: ${branch}"
        }
    }

    def createReleaseBranchLocally(String releaseVersion) {
        this.echo "createReleaseBranchLocally releaseVersion - ${releaseVersion}"
        this.sh "git status"
        this.sh "git flow release start ${releaseVersion}"
        this.echo "createReleaseBranchLocally finished"
    }

    def commitChanges(String message) {
        this.echo "commit changes with message: ${message}"
        this.sh "git status"
        this.sh "git add ."
        this.sh "git status"
        this.sh "git commit -m \'${message}\'"
        this.echo "successfully committed"
    }

    def switchToBranch(String branch) {
        this.echo "switchToBranch: ${branch} started"
        this.sh "git checkout ${branch}"
        this.echo "switchToBranch: ${branch} finished"
    }

    def createBranch(String branchName) {
        this.sh "git checkout -b ${branchName}"
        commitChanges("create new branch: ${branchName}")
    }

    def createAndPushBranch(String branchName) {
        this.echo "started createAndPushBranch: ${branchName}"
        this.sh "git checkout -b ${branchName}"
        this.sshagent([credentialsId]) {
            this.sh "git push -u origin ${branchName}"
        }
        this.echo "finished createAndPushBranch: ${branchName}"
    }

    def pushNewBranches() {
        this.echo "started pushNewBranches"
        this.sshagent([credentialsId]) {
            this.sh "git push --all -u"
        }
        this.echo "finished pushNewBranches"
    }

    def pushNewBranch(String branchName) {
        this.echo "started pushNewBranch: ${branchName}"
        this.sshagent([credentialsId]) {
            this.sh "git push -u origin ${branchName}"
        }
        this.echo "finished pushNewBranch: ${branchName}"
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
        this.sshagent([credentialsId]) {
            this.sh "git push"
            this.sh "git push --all"
            this.sh "git push origin --tags"
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

    GitService withUserName(String userName) {
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
        return "GitService{" +
                "credentialsId='" + credentialsId + '\'' +
                ", repo='" + repo + '\'' +
                ", repoOwner='" + repoOwner + '\'' +
                ", userName='" + userName + '\'' +
                "} " + super.toString();
    }
}
