package tech.ericwathome.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import tech.ericwathome.config.configureAndroidTests

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureAndroidTests(this)
            }

            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureAndroidTests(this)
            }
        }
    }
}