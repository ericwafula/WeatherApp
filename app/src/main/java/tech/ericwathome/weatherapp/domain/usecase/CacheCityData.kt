package tech.ericwathome.weatherapp.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import tech.ericwathome.core.domain.util.EmptyResult
import tech.ericwathome.core.domain.util.Result
import tech.ericwathome.core.domain.util.asEmptyDataResult
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.weatherapp.datasource.remote.weather.KtorRemoteWeatherDatasource
import tech.ericwathome.weatherapp.domain.util.DataError

class CacheCityData(
    private val localWeatherDataSource: LocalWeatherDataSource,
    private val remoteWeatherDatasource: KtorRemoteWeatherDatasource,
    private val applicationScope: CoroutineScope
) {
    suspend operator fun invoke(): EmptyResult<DataError> {
        return when (val result = remoteWeatherDatasource.fetchCities()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localWeatherDataSource.upsertCityData(result.data)
                }.await()
            }
        }
    }
}