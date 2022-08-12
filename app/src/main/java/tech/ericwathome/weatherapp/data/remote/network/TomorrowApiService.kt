package tech.ericwathome.weatherapp.data.remote.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import tech.ericwathome.weatherapp.data.model.WeatherInfo

interface TomorrowApiService {
    @GET("timelines")
    suspend fun getCurrentWeatherInfo(
        @QueryMap query: HashMap<String, String>
    ): Response<WeatherInfo>
}