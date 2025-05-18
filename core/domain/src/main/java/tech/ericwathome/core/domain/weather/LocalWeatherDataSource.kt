package tech.ericwathome.core.domain.weather

import kotlinx.coroutines.flow.Flow
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.weatherapp.domain.util.DataError
import tech.ericwathome.weatherapp.domain.util.EmptyResult

interface LocalWeatherDataSource {
    val weatherForecastObservable: Flow<Forecast>

    suspend fun upsertWeatherForecast(forecast: Forecast): EmptyResult<DataError.Local>

    suspend fun getWeatherForecast(): Forecast?
}