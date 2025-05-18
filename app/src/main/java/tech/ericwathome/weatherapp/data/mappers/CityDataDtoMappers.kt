package tech.ericwathome.weatherapp.data.mappers

import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.weatherapp.datasource.remote.dto.CityDataDto

fun CityDataDto.toDomain(): CityData {
    return CityData(
        country = country,
        name = name,
        lat = lat,
        lon = lon,
    )
}