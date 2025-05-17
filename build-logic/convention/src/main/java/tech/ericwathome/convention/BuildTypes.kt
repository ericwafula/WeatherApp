package tech.ericwathome.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun configureBuildTypes(
    project: Project,
    extensionType: ExtensionType,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true

            val properties  = gradleLocalProperties(project.rootDir, project.rootProject.providers)

            val openWeatherBaseUrl = properties.getProperty("OPEN_WEATHER_BASE_URL")
            val openWeatherApiKey = properties.getProperty("OPEN_WEATHER_API_KEY")
            val cityApi = properties.getProperty("CITY_API")

            when (extensionType) {
                ExtensionType.APPLICATION -> {
                    project.extensions.configure<ApplicationExtension> {
                        buildTypes {
                            debug { configureDebugBuild(openWeatherBaseUrl, openWeatherApiKey, cityApi) }
                            release { configureReleaseBuild(openWeatherBaseUrl, openWeatherApiKey, cityApi, commonExtension) }
                        }
                    }
                }
                ExtensionType.LIBRARY -> {
                    project.extensions.configure<LibraryExtension> {
                        buildTypes {
                            debug { configureDebugBuild(openWeatherBaseUrl, openWeatherApiKey, cityApi) }
                            release { configureReleaseBuild(openWeatherBaseUrl, openWeatherApiKey, cityApi, commonExtension) }
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuild(
    openWeatherBaseUrl: String,
    openWeatherApiKey: String,
    cityApi: String
) {
    isMinifyEnabled = false
    buildConfigField("String", "OPEN_WEATHER_BASE_URL", "\"$openWeatherBaseUrl\"")
    buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"$openWeatherApiKey\"")
    buildConfigField("String", "CITY_API", "\"$cityApi\"")
}

private fun BuildType.configureReleaseBuild(
    openWeatherBaseUrl: String,
    openWeatherApiKey: String,
    cityApi: String,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    isMinifyEnabled = true

    buildConfigField("String", "OPEN_WEATHER_BASE_URL", "\"$openWeatherBaseUrl\"")
    buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"$openWeatherApiKey\"")
    buildConfigField("String", "CITY_API", "\"$cityApi\"")

    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}