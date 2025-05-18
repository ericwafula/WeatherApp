package tech.ericwathome.core.domain.util

import kotlin.math.ceil

fun Double.kelvinToFormattedCelsius(): String {
    val result = this - 273.15
    val formattedResult = ceil(result).toInt()

    return when {
        result < 0 -> "${formattedResult}º"
        else -> "+${formattedResult}º"
    }
}