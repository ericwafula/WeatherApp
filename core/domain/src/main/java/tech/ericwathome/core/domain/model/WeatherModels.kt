package tech.ericwathome.core.domain.model

data class Forecast(
    val list: List<ForecastItem>,
    val dailySummary: String,
    val city: City,
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: Weather,
    val wind: Wind,
    val visibility: Long,
    val dtTxt: String,
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

data class Wind(
    val speed: Double,
)

data class City(
    val id: Long,
    val name: String,
    val country: String,
)