package tech.ericwathome.weatherapp.datasource.remote.util

import tech.ericwathome.weatherapp.datasource.remote.dto.ForecastItemDto
import kotlin.math.ceil

fun List<ForecastItemDto>.toFeelLikeTempCelsius(): String {
    return (getOrNull(0)?.main?.temp ?: 0.0).kelvinToFormattedCelsius()
}

fun List<ForecastItemDto>.toMinTempCelsius(): String {
    return (getOrNull(0)?.main?.minTemp ?: 0.0).kelvinToFormattedCelsius()
}

fun List<ForecastItemDto>.toMaxTempCelsius(): String {
    return (getOrNull(0)?.main?.maxTemp ?: 0.0).kelvinToFormattedCelsius()
}

fun Double.kelvinToFormattedCelsius(): String {
    val result = this - 273.15
    val formattedResult = ceil(result).toInt()

    return when {
        result < 0 -> "$formattedResult"
        else -> "+$formattedResult"
    }
}