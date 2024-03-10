package tech.ericwathome.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import tech.ericwathome.config.configureHilt
import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureHilt(this)
            }

            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureHilt(this)
            }
        }
    }
}