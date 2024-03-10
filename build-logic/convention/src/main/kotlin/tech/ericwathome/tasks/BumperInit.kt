package tech.ericwathome.tasks

import org.gradle.api.tasks.TaskAction
import tech.ericwathome.tasks.BaseBumperTask

abstract class BumperInit : BaseBumperTask() {

    @TaskAction
    fun bumperInit() {
        val versionName = if (project.hasProperty("versionName")) {
            properties["versionName"].toString()
        } else "1.0.0.0"

        val versionCode = if (project.hasProperty("versionCode")) {
            properties["versionCode"].toString()
        } else "1"

        save(versionName, versionCode)
    }

}