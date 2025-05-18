package tech.ericwathome.weatherapp.presentation.weatherscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WeatherViewModel() : ViewModel() {
    private val _event = Channel<WeatherEvent>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(WeatherState())
    val state =
        _state
            .onStart {
                loadWeatherData()
                initStateObservers()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = WeatherState(),
            )

    private val hasLocationPermission = MutableStateFlow(false)

    fun onAction(action: WeatherAction) {
        when (action) {
            WeatherAction.OnClickUseCurrentLocationNo -> onClickUseCurrentLocationNo()
            WeatherAction.OnClickUseCurrentLocationYes -> onClickUseCurrentLocationYes()
            else -> Unit
        }
    }

    private fun onClickUseCurrentLocationNo() {
        _state.update { it.copy(showUseCurrentLocationDialog = false) }
    }

    private fun onClickUseCurrentLocationYes() {
        _state.update { it.copy(showUseCurrentLocationDialog = false) }

        // use location services to fetch the user's current location
    }

    private fun initStateObservers() {
    }

    private fun loadWeatherData() {
    }
}