package tech.ericwathome.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroidTests(
    extension: CommonExtension<*, *, *, *, *>
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extension.apply {
        dependencies {
            "testImplementation"(libs.findLibrary("junit").get())
            "testImplementation"(libs.findLibrary("truth").get())
            "testImplementation"(libs.findLibrary("turbine").get())
            "testImplementation"(libs.findLibrary("kotlin-coroutines-test").get())
            "androidTestImplementation"(libs.findLibrary("androidx-test-ext-junit").get())
            "androidTestImplementation"(libs.findLibrary("truth").get())
            "androidTestImplementation"(libs.findLibrary("turbine").get())
            "androidTestImplementation"(libs.findLibrary("kotlin-coroutines-test").get())
        }
    }
}