package tech.ericwathome.weatherapp.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.core.domain.weather.RemoteWeatherDatasource
import tech.ericwathome.weatherapp.domain.util.DataError
import tech.ericwathome.weatherapp.domain.util.EmptyResult
import tech.ericwathome.weatherapp.domain.util.Result
import tech.ericwathome.weatherapp.domain.util.asEmptyDataResult

class CacheWeatherForecast(
    private val remoteWeatherDatasource: RemoteWeatherDatasource,
    private val localWeatherDataSource: LocalWeatherDataSource,
    private val applicationScope: CoroutineScope,
) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
    ): EmptyResult<DataError> {
        return when (val result = remoteWeatherDatasource.fetchWeatherForecast(lat, lon)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localWeatherDataSource.upsertWeatherForecast(result.data)
                }.await()
            }
        }
    }
}