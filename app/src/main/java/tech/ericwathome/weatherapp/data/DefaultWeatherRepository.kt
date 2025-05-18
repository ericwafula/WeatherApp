package tech.ericwathome.weatherapp.data

import kotlinx.coroutines.flow.Flow
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.core.domain.weather.WeatherRepository

class DefaultWeatherRepository(
    private val localWeatherDataSource: LocalWeatherDataSource,
) : WeatherRepository {
    override val weatherForecastObservable: Flow<Forecast>
        get() = localWeatherDataSource.weatherForecastObservable
}