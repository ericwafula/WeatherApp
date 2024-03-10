package tech.ericwathome.tasks

import org.gradle.api.tasks.TaskAction
import tech.ericwathome.tasks.BaseBumperTask

abstract class BumperVersionPatchTask : BaseBumperTask() {

    @TaskAction
    fun bumperVersionPatch() {
        val versionName =
            getVersionNameList[0] + "." + getVersionNameList[1] + "." + (getVersionNameList[2].toInt() + 1) + "." + 0
        val versionCode = incrementVersionCode()

        save(versionName, versionCode)
    }

}