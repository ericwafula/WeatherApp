plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

group = "tech.ericwathome"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("hiltAndroid") {
            id = "ericwathome.hilt.android"
            implementationClass = "tech.ericwathome.plugins.HiltAndroidConventionPlugin"
        }

        create("kotlinAndroid") {
            id = "ericwathome.kotlin.android"
            implementationClass = "tech.ericwathome.plugins.KotlinAndroidConventionPlugin"
        }

        create("AndroidLibrary") {
            id = "ericwathome.android.library"
            implementationClass = "tech.ericwathome.plugins.AndroidLibraryConventionPlugin"
        }

        create("AndroidTest") {
            id = "ericwathome.android.test"
            implementationClass = "tech.ericwathome.plugins.AndroidTestConventionPlugin"
        }

        create("AndroidData") {
            id = "ericwathome.android.data"
            implementationClass = "tech.ericwathome.plugins.AndroidDataConventionPlugin"
        }

        create("ComposeAndroid") {
            id = "ericwathome.compose.android"
            implementationClass = "tech.ericwathome.plugins.ComposeAndroidConventionPlugin"
        }
    }
}