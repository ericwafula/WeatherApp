package tech.ericwathome.weatherapp.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    val list: List<ForecastItemDto>,
    val city: CityDto,
)

@Serializable
data class ForecastItemDto(
    val dt: Long,
    val main: MainDto,
    val weather: List<WeatherDto>,
    val wind: WindDto,
    val visibility: Long,
    @SerialName("dt_txt")
    val dtTxt: String,
)

@Serializable
data class MainDto(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val minTemp: Double,
    @SerialName("temp_max")
    val maxTemp: Double,
    val humidity: Int,
)

@Serializable
data class WeatherDto(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
data class WindDto(
    val speed: Double,
)

@Serializable
data class CityDto(
    val id: Long,
    val name: String,
    val country: String,
)