package tech.ericwathome.tasks

import org.gradle.api.tasks.TaskAction
import tech.ericwathome.tasks.BaseBumperTask

abstract class BumperVersionHotfixTask : BaseBumperTask() {
    @TaskAction
    fun bumperVersionHotFix() {
        val versionName =
            getVersionNameList[0] + "." + getVersionNameList[1] + "." + getVersionNameList[2] + "." + (getVersionNameList[3].toInt() + 1).toString()
        val versionCode = incrementVersionCode()

        save(versionName, versionCode)
    }
}