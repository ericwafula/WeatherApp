package tech.ericwathome.weatherapp.presentation.searchlocation

sealed interface SearchLocationAction {
    data object OnClickCloseIcon : SearchLocationAction

    data class OnEnterSearchQuery(val query: String) : SearchLocationAction

    data class OnSelectLocation(val index: Int) : SearchLocationAction

    data object OnClickContinue : SearchLocationAction
}