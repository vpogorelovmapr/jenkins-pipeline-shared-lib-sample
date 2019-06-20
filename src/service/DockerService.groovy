package service

class DockerService {

//    static final LATEST_LABEL = "latest"
//
//    String devRepo
//    String prodRepo
//    String dockerDevCredentialsId
//    String dockerProdCredentialsId
//    DockerOrchestrationService orchestrationService
//    String customDockerFileName
//
//    DockerService(String devRepo, String prodRepo, DockerOrchestrationService orchestrationService) {
//        this.devRepo = devRepo
//        this.prodRepo = prodRepo
//        this.orchestrationService = orchestrationService
//    }
//
//    def dockerBuildImage(String imageName, String imageVersion) {
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo "dockerBuildImage for imageName: ${imageName} with imageVersion: ${imageVersion} started"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh 'docker -v'
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker build -t ${imageName}:${imageVersion} ${getCustomDockerFileInstruction()} ."
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo "dockerBuildImage for service: ${imageName} with releaseVersion: ${imageVersion} finished"
//    }
//
//    def dockerPublish(String imageName, String imageVersion, Env env) {
//        if (env == Env.DEV) {
//            dockerPushImageToRegistry(imageName, imageVersion, devRepo, dockerDevCredentialsId)
//            dockerPushImageWithBuildNumberToRegistryWithoutLogin(imageName, imageVersion, devRepo)
//        }
//        if (env == Env.QA) {
//            if (devRepo.equalsIgnoreCase(prodRepo)) {
//                pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo "prodRepo: ${prodRepo} same as ${devRepo}, dockerPushImageToRegistry will be skipped"
//                return
//            }
//            dockerPushImageToRegistry(imageName, imageVersion, prodRepo, dockerProdCredentialsId)
//        }
//    }
//
//    def dockerPushImageToProdRegistry(String imageName, String imageVersion) {
//        dockerPushImageToRegistry(imageName, imageVersion, prodRepo, dockerProdCredentialsId)
//    }
//
//    def dockerLoginAndPushImageToRegistry(String imageName, String imageVersion,
//                                          String dockerRepositoryUrl, String credentialsId) {
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo "withDockerRegistry([credentialsId: ${credentialsId}, url: 'http://${dockerRepositoryUrl}'])"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.withDockerRegistry([credentialsId: credentialsId, url: 'http://${dockerRepositoryUrl}']) {
//            pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker images"
//            pro.javatar.pipeline.service.PipelineDslHolder.dsl.docker.image("${dockerRepositoryUrl}/${imageName}:${imageVersion}").push()
//        }
//    }
//
//    def dockerPushImageToRegistryWithoutLogin(String imageName, String imageVersion, String dockerRepositoryUrl) {
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker images"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker tag ${imageName}:${imageVersion} ${dockerRepositoryUrl}/${imageName}:${imageVersion}"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker push ${dockerRepositoryUrl}/${imageName}:${imageVersion}"
//    }
//
//    def dockerPushImageWithBuildNumberToRegistryWithoutLogin(String imageName, String imageVersion,
//                                                             String dockerRepositoryUrl) {
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker images"
//        String versionWithBuildNumber = getImageVersionWithBuildNumber(imageVersion)
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker tag ${imageName}:${imageVersion} ${dockerRepositoryUrl}/${imageName}:${versionWithBuildNumber}"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker push ${dockerRepositoryUrl}/${imageName}:${versionWithBuildNumber}"
//    }
//
//    def dockerPushLatestImageToRegistryWithoutLogin(String imageName, String imageVersion, String dockerRepositoryUrl) {
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker images"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker tag ${imageName}:${imageVersion} ${dockerRepositoryUrl}/${imageName}:${LATEST_LABEL}"
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh "docker push ${dockerRepositoryUrl}/${imageName}:${LATEST_LABEL}"
//    }
//
//    def dockerPushImageToRegistry(String imageName, String imageVersion,
//                                  String dockerRepositoryUrl, String credentialsId) {
//        dockerLogin(dockerRepositoryUrl, credentialsId)
//        dockerPushImageToRegistryWithoutLogin(imageName, imageVersion, dockerRepositoryUrl)
//    }
//
//    def dockerLogin(String dockerRepositoryUrl, String credentialsId) {
//        if (pro.javatar.pipeline.util.Utils.isBlank(credentialsId)) {
//            pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo "WARN: credentialsId is blank (${credentialsId}), skip login"
//            return
//        }
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.withCredentials([[$class          : 'UsernamePasswordMultiBinding',
//                                                                             credentialsId   : credentialsId,
//                                                                             usernameVariable: 'DOCKER_REGISTRY_USERNAME',
//                                                                             passwordVariable: 'DOCKER_REGISTRY_PASSWORD']]) {
//            pro.javatar.pipeline.service.PipelineDslHolder.dsl.sh("echo ${pro.javatar.pipeline.service.PipelineDslHolder.dsl.env.DOCKER_REGISTRY_PASSWORD} | docker login ${dockerRepositoryUrl} -u ${pro.javatar.pipeline.service.PipelineDslHolder.dsl.env.DOCKER_REGISTRY_USERNAME} --password-stdin")
//        }
//    }
//
//    def dockerDeployContainer(String imageName, String imageVersion, Env env) {
//        pro.javatar.pipeline.service.PipelineDslHolder.dsl.echo "dockerDeployContainer(${imageName}, ${imageVersion}, ${env.getValue()})"
//        if (env == Env.DEV) {
//            orchestrationService.setup()
//            String versionWithBuildNumber = getImageVersionWithBuildNumber(imageVersion)
//            orchestrationService.dockerDeployContainer(imageName, versionWithBuildNumber, devRepo, env.getValue())
//        } else {
//            orchestrationService.dockerDeployContainer(imageName, imageVersion, prodRepo, env.getValue())
//        }
//    }
//
//    void setDockerDevCredentialsId(String dockerCredentialsId) {
//        this.dockerDevCredentialsId = dockerCredentialsId
//    }
//
//    void setDockerProdCredentialsId(String dockerProdCredentialsId) {
//        this.dockerProdCredentialsId = dockerProdCredentialsId
//    }
//
//    String getCustomDockerFileInstruction() {
//        if (pro.javatar.pipeline.util.Utils.isBlank(customDockerFileName)) {
//            return ""
//        }
//        return "-f ${customDockerFileName}"
//    }
//
//    void setDockerCredentialsId(String dockerCredentialsId) {
//        this.dockerCredentialsId = dockerCredentialsId
//    }
//
//    void setCustomDockerFileName(String customDockerFileName) {
//        this.customDockerFileName = customDockerFileName
//    }
//
//    String getImageVersionWithBuildNumber(String imageVersion) {
//        return "${imageVersion}.${pro.javatar.pipeline.service.PipelineDslHolder.dsl.currentBuild.number}"
//    }
//
//    @Override
//    public String toString() {
//        return "DockerService{" +
//                "devRepo='" + devRepo + '\'' +
//                ", prodRepo='" + prodRepo + '\'' +
//                '}';
//    }
}
