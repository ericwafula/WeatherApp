package tech.ericwathome.weatherapp.data.remote.repository

import tech.ericwathome.weatherapp.data.model.WeatherInfo
import tech.ericwathome.weatherapp.util.Resource

interface WeatherRepository {
    suspend fun getCurrentWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo>
}