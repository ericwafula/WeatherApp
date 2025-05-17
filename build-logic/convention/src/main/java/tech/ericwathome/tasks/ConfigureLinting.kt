package tech.ericwathome.tasks

import org.gradle.api.tasks.TaskContainer

fun configureLinting(
    container: TaskContainer
) {
    container.run {
        named("preBuild").configure {
            dependsOn("ktlintFormat")
        }
    }
}