package tech.ericwathome.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import tech.ericwathome.config.configureBumperTasks
import tech.ericwathome.config.configureKotlinAndroid

class KotlinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }

            with(tasks) {
                configureBumperTasks(this)
            }

            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureKotlinAndroid(this)
            }

            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureKotlinAndroid(this)
            }
        }
    }
}