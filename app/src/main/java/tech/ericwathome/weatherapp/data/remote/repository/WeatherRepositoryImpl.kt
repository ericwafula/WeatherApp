package tech.ericwathome.weatherapp.data.remote.repository

import tech.ericwathome.weatherapp.data.model.WeatherInfo
import tech.ericwathome.weatherapp.data.remote.network.TomorrowApiService
import tech.ericwathome.weatherapp.util.Resource
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: TomorrowApiService
): WeatherRepository {
    override suspend fun getCurrentWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            val map = buildMap {
                put("location", "$lat,$long")
                put("fields", "fields")
                put("timesteps", "1d")
                put("units", "metric")
            }
            val query = HashMap<String, String>()
            query.putAll(map)
            val response = apiService.getCurrentWeatherInfo(query)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("")
            }
        } catch (e: Exception) {
            Resource.Error("network error")
        }
    }
}