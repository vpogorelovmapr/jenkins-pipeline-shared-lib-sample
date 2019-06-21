package resolver

import model.VcsRepositoryType

class VcsRepositoryUrlResolver {

    VcsRepositoryType vcsRepositoryType

    boolean useSsh = true

    RevisionControlService revisionControlService

    VcsRepositoryUrlResolver(VcsRepositoryType vcsRepositoryType,
                             boolean useSsh,
                             RevisionControlService revisionControlService) {
        this.vcsRepositoryType = vcsRepositoryType
        this.useSsh = useSsh
        this.revisionControlService = revisionControlService
    }

    VcsRepositoryUrlResolver(VcsRepositoryType vcsRepositoryType) {
        this.vcsRepositoryType = vcsRepositoryType
    }

    String getRepoUrl() {
        String repoName = revisionControlService.getRepoName()
        String repoOwner = revisionControlService.getRepoOwner()

        return getRepoUrl(repoOwner, repoName)
    }

    String getRepoUrl(String repoOwner, String repoName) {
        String userName = revisionControlService.getUserName()
        String domain = revisionControlService.getDomain()

        if (vcsRepositoryType == VcsRepositoryType.GITLAB) {
            if (useSsh) {
                return "git@${domain}:${repoOwner}/${repoName}.git"
            }
            throw new UnsupportedOperationException("gitlab https is not yet implemented")
        }
        if (vcsRepositoryType == VcsRepositoryType.GITHUB) {
            if (useSsh) {
                return "git@${domain}:${repoOwner}/${repoName}.git"
            }
            throw new UnsupportedOperationException("github https is not yet implemented")
        }
        if (vcsRepositoryType == VcsRepositoryType.BITBUCKET) {
            if (useSsh) {
                return "ssh://git@${domain}/${repoOwner}/${repoName}.git"
            }
            return "https://${userName}@bitbucket.org/${repoOwner}/${repoName}.git/"
        }
    }
}
