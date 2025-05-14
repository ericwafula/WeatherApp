plugins {
    alias(libs.plugins.weatherapp.android.library.compose)
}

android {
    namespace = "tech.ericwathome.core.ui"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.timber)
    implementation(projects.core.designsystem)
}