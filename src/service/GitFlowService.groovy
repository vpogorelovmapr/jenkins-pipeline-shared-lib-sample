package service

class GitFlowService {

    GitFlowService(GitService gitService) {
        super(gitService)
    }

//    @Override
    def initDefaultFlow() {
        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh 'git flow init -df'
    }

//    @Override
    def initFlowWithPrefix(String flowPrefix) {
        String initGitFlowCommand = "echo '${flowPrefix}-master/\\n${flowPrefix}-develop/\\n${flowPrefix}-feature/\\n" +
                "${flowPrefix}-bugfix/\\n${flowPrefix}-release/\\n${flowPrefix}-hotfix/\\n${flowPrefix}-support/" +
                "\\n${flowPrefix}-' | git flow init -f"
        pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo initGitFlowCommand
        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh initGitFlowCommand
        revService.showConfigFile()
    }

}