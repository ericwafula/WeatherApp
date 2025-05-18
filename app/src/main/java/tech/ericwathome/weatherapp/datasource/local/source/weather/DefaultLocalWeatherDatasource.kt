package tech.ericwathome.weatherapp.datasource.local.source.weather

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.weatherapp.data.mappers.toDomain
import tech.ericwathome.weatherapp.data.mappers.toEntity
import tech.ericwathome.weatherapp.datasource.local.util.safeTransaction
import tech.ericwathome.weatherapp.domain.util.DataError
import tech.ericwathome.weatherapp.domain.util.EmptyResult

class DefaultLocalWeatherDatasource(
    private val forecastDao: ForecastDao,
) : LocalWeatherDataSource {
    override val weatherForecastObservable: Flow<Forecast>
        get() = forecastDao.observeForecast().map { it.toDomain() }

    override suspend fun upsertWeatherForecast(forecast: Forecast): EmptyResult<DataError.Local> {
        return safeTransaction {
            forecastDao.upsertForecast(forecast.toEntity())
        }
    }

    override suspend fun getWeatherForecast(): Forecast? {
        return forecastDao.getForecast()?.toDomain()
    }
}