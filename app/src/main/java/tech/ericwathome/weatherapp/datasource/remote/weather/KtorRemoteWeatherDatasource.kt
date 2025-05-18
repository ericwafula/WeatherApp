package tech.ericwathome.weatherapp.datasource.remote.weather

import io.ktor.client.HttpClient
import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.util.Result
import tech.ericwathome.core.domain.util.map
import tech.ericwathome.core.domain.weather.RemoteWeatherDatasource
import tech.ericwathome.weatherapp.BuildConfig
import tech.ericwathome.weatherapp.data.mappers.toDomain
import tech.ericwathome.weatherapp.data.network.get
import tech.ericwathome.weatherapp.datasource.remote.dto.CityDataDto
import tech.ericwathome.weatherapp.datasource.remote.dto.ForecastDto
import tech.ericwathome.weatherapp.domain.util.DataError

class KtorRemoteWeatherDatasource(
    private val httpClient: HttpClient,
) : RemoteWeatherDatasource {
    override suspend fun fetchWeatherForecast(
        lat: Double,
        lon: Double,
    ): Result<Forecast, DataError.Network> {
        return httpClient.get<ForecastDto>(
            route = BuildConfig.OPEN_WEATHER_BASE_URL + "forecast",
            queryParameters =
                mapOf(
                    "lat" to lat,
                    "lon" to lon,
                    "appid" to BuildConfig.OPEN_WEATHER_API_KEY,
                ),
        ).map { it.toDomain() }
    }

    override suspend fun fetchCities(): Result<List<CityData>, DataError.Network> {
        return httpClient.get<List<CityDataDto>>(
            route = BuildConfig.CITY_API
        ).map { list -> list.map { it.toDomain() } }
    }
}