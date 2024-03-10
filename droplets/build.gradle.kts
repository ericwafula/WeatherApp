@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.ericwathome.android.library)
    alias(libs.plugins.ericwathome.kotlin.android)
    alias(libs.plugins.ericwathome.android.test)
    alias(libs.plugins.ericwathome.compose.android)
}

android {
    namespace = "tech.ericwathome.droplets"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}