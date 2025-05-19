package tech.ericwathome.weatherapp.presentation.weatherscreen

sealed interface WeatherAction {
    data object OnClickUseCurrentLocationNo : WeatherAction

    data object OnClickUseCurrentLocationYes : WeatherAction

    data object OnClickSearchIcon : WeatherAction

    data object OnDismissBottomSheet : WeatherAction

    data class OnEnterCityName(val name: String) : WeatherAction

    data object OnClickContinue : WeatherAction

    data class SubmitLocationPermissionInfo(
        val showLocationRationale: Boolean,
        val isPermissionGranted: Boolean,
    ) : WeatherAction

    data object OnDismissLocationRationale : WeatherAction
}