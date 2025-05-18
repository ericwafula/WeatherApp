package tech.ericwathome.weatherapp.data.mappers

import tech.ericwathome.core.domain.model.City
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.model.ForecastItem
import tech.ericwathome.core.domain.model.Main
import tech.ericwathome.core.domain.model.Weather
import tech.ericwathome.core.domain.model.Wind
import tech.ericwathome.weatherapp.datasource.local.entity.CityEntity
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastEntity
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastItemEntity
import tech.ericwathome.weatherapp.datasource.local.entity.MainEntity
import tech.ericwathome.weatherapp.datasource.local.entity.WeatherEntity
import tech.ericwathome.weatherapp.datasource.local.entity.WindEntity

fun ForecastEntity.toDomain(): Forecast {
    return Forecast(
        list = list.map { it.toDomain() },
        dailySummary = dailySummary,
        city = city.toDomain(),
    )
}

fun Forecast.toEntity(): ForecastEntity {
    return ForecastEntity(
        list = list.map { it.toEntity() },
        dailySummary = dailySummary,
        city = city.toEntity(),
    )
}

fun ForecastItemEntity.toDomain(): ForecastItem {
    return ForecastItem(
        dt = dt,
        main = main.toDomain(),
        weather = weather.map { it.toDomain() },
        wind = wind.toDomain(),
        visibility = visibility,
        dtTxt = dtTxt,
    )
}

fun MainEntity.toDomain(): Main {
    return Main(
        temp = temp,
        feelsLike = feelsLike,
        minTemp = minTemp,
        maxTemp = maxTemp,
        humidity = humidity,
    )
}

fun WeatherEntity.toDomain(): Weather {
    return Weather(
        id = id,
        main = main,
        description = description,
        icon = icon,
    )
}

fun CityEntity.toDomain(): City {
    return City(
        id = id,
        name = name,
        country = country,
    )
}

fun WindEntity.toDomain(): Wind {
    return Wind(speed = speed)
}

fun ForecastItem.toEntity(): ForecastItemEntity {
    return ForecastItemEntity(
        dt = dt,
        main = main.toEntity(),
        weather = weather.map { it.toEntity() },
        wind = wind.toEntity(),
        visibility = visibility,
        dtTxt = dtTxt,
    )
}

fun Main.toEntity(): MainEntity {
    return MainEntity(
        temp = temp,
        feelsLike = feelsLike,
        minTemp = minTemp,
        maxTemp = maxTemp,
        humidity = humidity,
    )
}

fun Weather.toEntity(): WeatherEntity {
    return WeatherEntity(
        id = id,
        main = main,
        description = description,
        icon = icon,
    )
}

fun Wind.toEntity(): WindEntity {
    return WindEntity(speed = speed)
}

fun City.toEntity(): CityEntity {
    return CityEntity(
        id = id,
        name = name,
        country = country,
    )
}