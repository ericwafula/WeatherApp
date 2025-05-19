package tech.ericwathome.weatherapp.datasource.remote.weather

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.util.Result
import tech.ericwathome.core.domain.util.map
import tech.ericwathome.core.domain.weather.RemoteWeatherDatasource
import tech.ericwathome.weatherapp.BuildConfig
import tech.ericwathome.weatherapp.data.mappers.toDomain
import tech.ericwathome.weatherapp.data.network.get
import tech.ericwathome.weatherapp.datasource.remote.dto.ForecastDto
import tech.ericwathome.weatherapp.domain.util.DataError
import tech.ericwathome.weatherapp.domain.util.DispatcherProvider

class KtorRemoteWeatherDatasource(
    private val httpClient: HttpClient,
    private val dispatchers: DispatcherProvider,
) : RemoteWeatherDatasource {
    override suspend fun fetchWeatherForecast(
        lat: Double?,
        lon: Double?,
        city: String,
    ): Result<Forecast, DataError.Network> {
        return withContext(dispatchers.io) {
            httpClient.get<ForecastDto>(
                route = BuildConfig.OPEN_WEATHER_BASE_URL + "forecast",
                queryParameters =
                    mapOf(
                        "lat" to lat,
                        "lon" to lon,
                        "q" to city,
                        "appid" to BuildConfig.OPEN_WEATHER_API_KEY,
                    ),
            ).map { it.toDomain() }
        }
    }
}