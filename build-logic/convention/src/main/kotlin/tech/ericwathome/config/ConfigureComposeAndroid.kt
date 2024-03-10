package tech.ericwathome.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import tech.ericwathome.utils.ProjectConfig

fun Project.configureComposeAndroid(
    extension: CommonExtension<*, *, *, *, *>
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extension.apply {
        defaultConfig {
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
        }

        dependencies {
            val composeBom = platform(libs.findLibrary("compose-bom").get())
            "implementation"(libs.findLibrary("lifecycle-runtime-ktx").get())
            "implementation"(libs.findLibrary("activity-compose").get())
            "implementation"(composeBom)
            "implementation"(libs.findLibrary("ui").get())
            "implementation"(libs.findLibrary("ui-graphics").get())
            "implementation"(libs.findLibrary("ui-tooling-preview").get())
            "implementation"(libs.findLibrary("material3").get())
            "androidTestImplementation"(libs.findLibrary("espresso-core").get())
            "androidTestImplementation"(composeBom)
            "androidTestImplementation"(libs.findLibrary("ui-test-junit4").get())
            "debugImplementation"(libs.findLibrary("ui-tooling").get())
            "debugImplementation"(libs.findLibrary("ui-test-manifest").get())
        }
    }
}