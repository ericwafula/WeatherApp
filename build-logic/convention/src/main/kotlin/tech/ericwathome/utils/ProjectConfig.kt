package tech.ericwathome.utils

import org.gradle.api.JavaVersion

object ProjectConfig {
    const val compileSdk = 34
    const val minSdk = 27
    const val targetSdk = 34
    val javaVersion = JavaVersion.VERSION_17
    const val kotlinCompilerExtensionVersion = "1.5.10"
}