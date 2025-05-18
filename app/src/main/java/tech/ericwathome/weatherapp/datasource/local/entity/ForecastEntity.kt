package tech.ericwathome.weatherapp.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ForecastEntity(
    @PrimaryKey
    val id: Long = 1,
    val list: List<ForecastItemEntity>,
    val dailySummary: String,
    val city: CityEntity,
)

@Serializable
data class ForecastItemEntity(
    val dt: Long,
    val main: MainEntity,
    val weather: List<WeatherEntity>,
    val wind: WindEntity,
    val visibility: Long,
    val dtTxt: String,
)

@Serializable
data class MainEntity(
    val temp: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
)

@Serializable
data class WeatherEntity(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
data class WindEntity(
    val speed: Double,
)

@Serializable
data class CityEntity(
    val id: Long,
    val name: String,
    val country: String,
)