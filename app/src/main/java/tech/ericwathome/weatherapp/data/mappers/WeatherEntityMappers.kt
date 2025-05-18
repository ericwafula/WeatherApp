package tech.ericwathome.weatherapp.data.mappers

import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastEntity

fun ForecastEntity.toDomain(): Forecast {
    return Forecast(
        list = list,
        dailySummary = dailySummary,
        city = city,
    )
}

fun Forecast.toEntity(): ForecastEntity {
    return ForecastEntity(
        list = list,
        dailySummary = dailySummary,
        city = city,
    )
}