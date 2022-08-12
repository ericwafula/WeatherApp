package tech.ericwathome.weatherapp.data.remote.network

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TomorrowApiService {
    @GET("timelines")
    suspend fun getCurrentWeatherInfo(
        @QueryMap query: HashMap<String, String>
    )
}