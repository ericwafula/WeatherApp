package tech.ericwathome.weatherapp.datasource.remote.util

import tech.ericwathome.core.domain.util.kelvinToFormattedCelsius
import tech.ericwathome.weatherapp.datasource.remote.dto.ForecastItemDto

fun List<ForecastItemDto>.toFeelLikeTempCelsius(): String {
    return (getOrNull(0)?.main?.temp ?: 0.0).kelvinToFormattedCelsius()
}

fun List<ForecastItemDto>.toMinTempCelsius(): String {
    return (getOrNull(0)?.main?.minTemp ?: 0.0).kelvinToFormattedCelsius()
}

fun List<ForecastItemDto>.toMaxTempCelsius(): String {
    return (getOrNull(0)?.main?.maxTemp ?: 0.0).kelvinToFormattedCelsius()
}