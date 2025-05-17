plugins {
    alias(libs.plugins.weatherapp.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}