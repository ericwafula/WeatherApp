package tech.ericwathome.weatherapp.data.mappers

import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.weatherapp.datasource.local.entity.CityDataEntity

fun CityDataEntity.toDomain(): CityData {
    return CityData(
        country = country,
        name = name,
        lat = lat,
        lon = lon,
    )
}

fun CityData.toEntity(): CityDataEntity {
    return CityDataEntity(
        country = country,
        name = name,
        lat = lat,
        lon = lon,
    )
}