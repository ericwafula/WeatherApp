package tech.ericwathome.weatherapp.data.remote.repository

import org.junit.Assert.*
import org.junit.Before
import tech.ericwathome.weatherapp.data.model.WeatherInfo
import tech.ericwathome.weatherapp.util.Resource

class WeatherRepositoryImplTest: WeatherRepository {

    override suspend fun getCurrentWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        TODO("Not yet implemented")
    }
}