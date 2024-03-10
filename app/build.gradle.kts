import tech.ericwathome.utils.ProjectConfig
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.ericwathome.kotlin.android)
    alias(libs.plugins.ericwathome.hilt)
    alias(libs.plugins.ericwathome.android.test)
    alias(libs.plugins.ericwathome.compose.android)
}

android {
    namespace = "tech.ericwathome.weatherapp"

    val versionFile = rootProject.file("version.properties")
    val versionProperties = Properties().apply {
        if (versionFile.exists()) {
            load(versionFile.inputStream())
        } else {
            throw IllegalArgumentException("version.properties not found!")
        }
    }

    val appVersionCode = versionProperties.getProperty("versionCode").toInt()
    val appVersionName = versionProperties.getProperty("versionName")

    defaultConfig {
        applicationId = "tech.ericwathome.weatherapp"
        targetSdk = ProjectConfig.targetSdk
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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
    implementation(projects.core.coreData)
    implementation(projects.core.coreDomain)
    implementation(projects.core.corePresentation)
    implementation(projects.network)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)
    implementation(projects.droplets)
}