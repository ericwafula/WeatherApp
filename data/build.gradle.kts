@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.ericwathome.android.library)
    alias(libs.plugins.ericwathome.hilt)
    alias(libs.plugins.ericwathome.kotlin.android)
    alias(libs.plugins.ericwathome.android.test)
    alias(libs.plugins.ericwathome.android.data)
}

android {
    namespace = "tech.ericwathome.data"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.network)
    implementation(projects.domain)
    implementation(projects.core.coreDomain)
}