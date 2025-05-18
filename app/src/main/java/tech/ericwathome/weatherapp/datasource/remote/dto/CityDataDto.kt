package tech.ericwathome.weatherapp.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDataDto(
    val country: String,
    val name: String,
    val lat: Double,
    @SerialName("lng")
    val lon: Double
)
