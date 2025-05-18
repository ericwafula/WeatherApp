package tech.ericwathome.weatherapp.presentation.weatherscreen

sealed interface WeatherAction {
    data object OnClickUseCurrentLocationNo : WeatherAction

    data object OnClickUseCurrentLocationYes : WeatherAction

    data object OnClickSearchIcon : WeatherAction

    data class SubmitLocationPermissionInfo(
        val showLocationRationale: Boolean,
        val isPermissionGranted: Boolean,
    ) : WeatherAction

    data object OnDismissLocationRationale : WeatherAction
}