package tech.ericwathome.weatherapp.data.remote.repository

import tech.ericwathome.weatherapp.data.model.WeatherInfo
import tech.ericwathome.weatherapp.data.remote.network.TomorrowApiService
import tech.ericwathome.weatherapp.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    apiService: TomorrowApiService
): WeatherRepository {
    override suspend fun getCurrentWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        TODO("Not yet implemented")
    }
}