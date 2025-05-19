package tech.ericwathome.core.domain.weather

import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.util.Result
import tech.ericwathome.weatherapp.domain.util.DataError

interface RemoteWeatherDatasource {
    suspend fun fetchWeatherForecast(
        lat: Double?,
        lon: Double?,
        city: String,
    ): Result<Forecast, DataError.Network>
}