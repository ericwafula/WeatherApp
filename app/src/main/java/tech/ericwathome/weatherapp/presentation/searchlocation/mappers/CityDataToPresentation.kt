package tech.ericwathome.weatherapp.presentation.searchlocation.mappers

import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.weatherapp.presentation.searchlocation.CityDataState

fun CityData.toPresentation(): CityDataState {
    return CityDataState(
        country = country,
        name = name,
        lat = lat,
        lon = lon,
        isSelected = isSelected,
    )
}