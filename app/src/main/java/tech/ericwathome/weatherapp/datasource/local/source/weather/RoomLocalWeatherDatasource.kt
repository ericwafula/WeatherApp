package tech.ericwathome.weatherapp.datasource.local.source.weather

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.util.EmptyResult
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.weatherapp.data.mappers.toDomain
import tech.ericwathome.weatherapp.data.mappers.toEntity
import tech.ericwathome.weatherapp.datasource.local.util.safeTransaction
import tech.ericwathome.weatherapp.domain.util.DataError

class RoomLocalWeatherDatasource(
    private val forecastDao: ForecastDao,
    private val cityDataDao: CityDataDao,
) : LocalWeatherDataSource {
    override val weatherForecastObservable: Flow<Forecast?>
        get() = forecastDao.observeForecast().map { it?.toDomain() }
    override val cityDataObservable: Flow<List<CityData>?>
        get() = cityDataDao.observeCityData().map { list -> list?.map { it.toDomain() } }

    override suspend fun upsertWeatherForecast(forecast: Forecast): EmptyResult<DataError.Local> {
        return safeTransaction {
            forecastDao.upsertForecast(forecast.toEntity())
        }
    }

    override suspend fun getWeatherForecast(): Forecast? {
        return forecastDao.getForecast()?.toDomain()
    }

    override suspend fun upsertCityData(data: List<CityData>): EmptyResult<DataError.Local> {
        return safeTransaction {
            cityDataDao.upsertCityData(data.map { it.toEntity() })
        }
    }
}