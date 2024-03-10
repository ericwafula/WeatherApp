package tech.ericwathome.config

import org.gradle.api.tasks.TaskContainer
import tech.ericwathome.tasks.BumperInit
import tech.ericwathome.tasks.BumperVersionHotfixTask
import tech.ericwathome.tasks.BumperVersionMajorTask
import tech.ericwathome.tasks.BumperVersionMinorTask
import tech.ericwathome.tasks.BumperVersionPatchTask

fun configureBumperTasks(
    container: TaskContainer
) {
    container.apply {
        create("bumperInit", BumperInit::class.java) {
            group = "bumper"
            description = "Initializes the version code and version name"
        }

        create("bumperVersionHotFix", BumperVersionHotfixTask::class.java) {
            group = "bumper"
            description = "Bumps the version code and version name for a hotfix"
        }

        create("bumperVersionPatch", BumperVersionPatchTask::class.java) {
            group = "bumper"
            description = "Bumps the version code and version name for a patch"
        }

        create("bumperVersionMinor", BumperVersionMinorTask::class.java) {
            group = "bumper"
            description = "Bumps the version code and version name for a minor release"
        }

        create("bumperVersionMajor", BumperVersionMajorTask::class.java) {
            group = "bumper"
            description = "Bumps the version code and version name for a major release"
        }
    }
}