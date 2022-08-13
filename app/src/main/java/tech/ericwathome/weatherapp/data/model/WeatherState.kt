package tech.ericwathome.weatherapp.data.model

data class WeatherState(
    var weatherInfo: WeatherInfo? = null,
    var isLoading: Boolean = false,
    var error: String? = null
)