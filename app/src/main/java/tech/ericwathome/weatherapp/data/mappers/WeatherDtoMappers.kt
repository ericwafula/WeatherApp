package tech.ericwathome.weatherapp.data.mappers

import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.model.ForecastItem
import tech.ericwathome.core.domain.model.Main
import tech.ericwathome.core.domain.model.Weather
import tech.ericwathome.core.domain.model.Wind
import tech.ericwathome.weatherapp.datasource.remote.dto.ForecastDto
import tech.ericwathome.weatherapp.datasource.remote.dto.ForecastItemDto
import tech.ericwathome.weatherapp.datasource.remote.dto.MainDto
import tech.ericwathome.weatherapp.datasource.remote.dto.WeatherDto
import tech.ericwathome.weatherapp.datasource.remote.dto.WindDto
import tech.ericwathome.weatherapp.datasource.remote.util.toFeelLikeTempCelsius
import tech.ericwathome.weatherapp.datasource.remote.util.toMaxTempCelsius
import tech.ericwathome.weatherapp.datasource.remote.util.toMinTempCelsius

fun ForecastDto.toDomain(): Forecast {
    return Forecast(
        list = list.map { it.toDomain() },
        dailySummary =
            "Now it feels like ${list.toFeelLikeTempCelsius()}º, actually ${list.toMinTempCelsius()}º." +
                "${list.getOrNull(0)?.weather?.description ?: ""} today, " +
                "temperatures ranging from ${list.toMinTempCelsius()}º to ${list.toMaxTempCelsius()}º.",
    )
}

fun ForecastItemDto.toDomain(): ForecastItem {
    return ForecastItem(
        dt = dt,
        main = main.toDomain(),
        weather = weather.toDomain(),
        wind = wind.toDomain(),
        visibility = visibility,
        dtTxt = dtTxt,
    )
}

fun MainDto.toDomain(): Main {
    return Main(
        temp = temp,
        feelsLike = feelsLike,
        minTemp = minTemp,
        maxTemp = maxTemp,
        humidity = humidity,
    )
}

fun WeatherDto.toDomain(): Weather {
    return Weather(
        id = id,
        main = main,
        description = description,
        icon = icon,
    )
}

fun WindDto.toDomain(): Wind {
    return Wind(speed = speed)
}