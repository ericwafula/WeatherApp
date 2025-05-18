package tech.ericwathome.core.domain.weather

import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.weatherapp.domain.util.DataError
import tech.ericwathome.weatherapp.domain.util.Result

interface RemoteWeatherDatasource {
    suspend fun fetchWeatherForecast(
        lat: Double,
        lon: Double,
    ): Result<Forecast, DataError.Network>
}