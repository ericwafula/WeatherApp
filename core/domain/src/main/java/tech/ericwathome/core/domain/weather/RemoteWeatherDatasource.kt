package tech.ericwathome.core.domain.weather

import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.util.Result
import tech.ericwathome.weatherapp.domain.util.DataError

interface RemoteWeatherDatasource {
    suspend fun fetchWeatherForecast(
        lat: Double,
        lon: Double,
    ): Result<Forecast, DataError.Network>

    suspend fun fetchCities(): Result<List<CityData>, DataError.Network>
}