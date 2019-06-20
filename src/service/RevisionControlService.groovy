/**
 * Copyright Javatar LLC 2018 ©
 * Licensed under the License located in the root of this repository (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://github.com/JavatarPro/pipeline-utils/blob/master/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package service

abstract class RevisionControlService {

    public static final String BRANCH_MASTER = "master"
    public static final String BRANCH_DEFAULT = "default"
    public static final String BRANCH_DEVELOP = "develop"

    private static final String DEFAULT_CHECKOUT_FOLDER = "repo"
    private String folder = DEFAULT_CHECKOUT_FOLDER

    protected Script script

    RevisionControlService(Script script) {
        this.script = script
    }

    public void cleanUp() {
        script.echo "start cleanup"
        script.sh "pwd; ls -la"
        script.sh "rm -rf ${folder}"
        script.sh "rm -rf ${folder}@tmp"
        script.sh "mkdir ${folder}"
        script.sh "pwd; ls -la"
        script.echo "end cleanup"
    }

    abstract def setUp()

    abstract def setUpVcsFlowPreparations()

    abstract def checkout(String branch)

    abstract def checkoutRepo(String repoOwner, String repo, String branch)

    abstract def checkoutRepo(String repoUrl, String branch)

}
