package tech.ericwathome.weatherapp.presentation.searchlocation

import tech.ericwathome.core.ui.UiText

sealed interface SearchLocationEvent {
    data class OnSelectCityData(val cityData: CityDataState) : SearchLocationEvent

    data class ShowToast(val message: UiText) : SearchLocationEvent
}