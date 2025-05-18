package tech.ericwathome.weatherapp.presentation.weatherscreen

import tech.ericwathome.core.ui.UiText

sealed interface WeatherEvent {
    data class ShowMessage(val uiText: UiText) : WeatherEvent
}