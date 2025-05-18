package tech.ericwathome.weatherapp.datasource.remote.dto

import kotlinx.serialization.SerialName

data class ForecastDto(
    val list: List<ForecastItemDto>,
    val city: CityDto,
)

data class ForecastItemDto(
    val dt: Long,
    val main: MainDto,
    val weather: WeatherDto,
    val wind: WindDto,
    val visibility: Long,
    @SerialName("dt_txt")
    val dtTxt: String,
)

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

data class WeatherDto(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

data class WindDto(
    val speed: Double,
)

data class CityDto(
    val id: Long,
    val name: String,
    val country: String,
)