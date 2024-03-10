package tech.ericwathome.config

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroidData(
    extension: LibraryExtension
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extension.apply {
        dependencies {
            "implementation"(libs.findLibrary("retrofit").get())
            "implementation"(libs.findLibrary("kotlinx-serialization").get())
            "implementation"(libs.findLibrary("retrofit2-kotlinx-serialization-converter").get())
            "implementation"(libs.findLibrary("logging-interceptor").get())
            "implementation"(libs.findLibrary("play-services-location").get())
        }
    }
}