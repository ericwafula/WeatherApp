package tech.ericwathome.weatherapp.presentation.weatherscreen

sealed interface WeatherAction {
    data object OnClickUseCurrentLocationNo : WeatherAction

    data object OnClickUseCurrentLocationYes : WeatherAction

    data object OnClickSearchIcon : WeatherAction
}