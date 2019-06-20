package service

abstract class BuildService implements Serializable {

    protected String distributionFolder = "dist"

    abstract void setUp()

    String getArtifact() {
        return "${distributionFolder}.zip"
    }

    abstract String getCurrentVersion()

    abstract def setupReleaseVersion(String releaseVersion)

    abstract def setupVersion(String version)

    void setDistributionFolder(String distributionFolder) {
        this.distributionFolder = distributionFolder
    }

    protected BuildService withUnitTestsTimeout(int unitTestsTimeout) {
        this.unitTestsTimeout = unitTestsTimeout
        return this
    }

}
