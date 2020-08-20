import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
!!!!! The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.1"

project {

    buildType(FlakyTests)
}

object FlakyTests : BuildType({
    name = "Flaky Tests"

    params {
        param("shouldFail", "false")
        param("duration", "300")
    }

    steps {
        script {
            scriptContent = """
                echo "##teamcity[testStarted name='myTest1' captureStandardOutput='true']"
                echo "##teamcity[testFinished name='myTest1' duration='%duration%']"
                echo "##teamcity[testStarted name='myTest2' captureStandardOutput='true']"
                if [ %shouldFail% = true ]
                then
                echo "##teamcity[testFailed name='myTest2']"
                fi
                echo "##teamcity[testFinished name='myTest2' duration='%duration%']"
            """.trimIndent()
        }
    }
})
