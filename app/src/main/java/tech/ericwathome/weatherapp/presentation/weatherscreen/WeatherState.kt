package tech.ericwathome.weatherapp.presentation.weatherscreen

import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.model.Location

data class WeatherState(
    val location: Location? = null,
    val loading: Boolean = false,
    val forecast: Forecast? = null,
    val showUseCurrentLocationDialog: Boolean = false,
    val showLocationRationale: Boolean = false,
)
