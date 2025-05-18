package tech.ericwathome.weatherapp.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.model.ForecastItem
import tech.ericwathome.core.domain.util.EmptyResult
import tech.ericwathome.core.domain.util.Result
import tech.ericwathome.core.domain.util.asEmptyDataResult
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.core.domain.weather.RemoteWeatherDatasource
import tech.ericwathome.weatherapp.domain.util.DataError
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
                    localWeatherDataSource.upsertWeatherForecast(result.data.copy(list = getFiveDayForecast(result.data)))
                }.await()
            }
        }
    }

    private fun getFiveDayForecast(data: Forecast): List<ForecastItem> {
        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneOffset.UTC)
        val today = LocalDate.now()
        val groupedByDay = LinkedHashMap<LocalDate, ForecastItem>()
        var todayForecast: ForecastItem? = null
        val zoneId = ZoneId.systemDefault()

        for (item in data.list) {
            val instant = inputFormatter.parse(item.dtTxt, Instant::from)
            val localDate = instant.atZone(zoneId).toLocalDate()

            if (localDate == today && todayForecast == null) {
                todayForecast = item
            }

            if (!groupedByDay.containsKey(localDate)) {
                groupedByDay[localDate] = item
            }

            if (groupedByDay.size >= 5) break
        }

        if (todayForecast != null && !groupedByDay.containsKey(today)) {
            val updated = LinkedHashMap<LocalDate, ForecastItem>()
            updated[today] = todayForecast
            updated.putAll(groupedByDay)
            return updated.values.take(5).toList()
        }

        return groupedByDay.values.take(5).toList()
    }
}