package tech.ericwathome.weatherapp.data.model

open class WeatherEvent {
    class Success(val resultText: String? = null, val weatherInfo: WeatherInfo): WeatherEvent()
    class Failure(val errorText: String): WeatherEvent()
    object Loading : WeatherEvent()
    object Empty : WeatherEvent()
}