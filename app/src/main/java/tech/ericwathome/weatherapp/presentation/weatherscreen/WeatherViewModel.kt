package tech.ericwathome.weatherapp.presentation.weatherscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.ericwathome.core.domain.LocationObserver
import tech.ericwathome.core.domain.model.Location
import tech.ericwathome.core.domain.util.onError
import tech.ericwathome.core.domain.util.onSuccess
import tech.ericwathome.core.domain.weather.WeatherRepository
import tech.ericwathome.core.ui.UiText
import tech.ericwathome.weatherapp.R
import tech.ericwathome.weatherapp.domain.usecase.CacheWeatherForecast
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModel(
    private val locationObserver: LocationObserver,
    private val cacheWeatherForecast: CacheWeatherForecast,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val _event = Channel<WeatherEvent>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(WeatherState())
    val state =
        _state
            .onStart {
                initStateObservers()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = WeatherState(),
            )

    private val hasLocationPermission = MutableStateFlow(false)
    private val currentUserLocation =
        hasLocationPermission.flatMapLatest { hasPermission ->
            if (hasPermission) {
                locationObserver.getLiveLocation(3_600_000)
            } else {
                flowOf()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null,
        )

    fun onAction(action: WeatherAction) {
        when (action) {
            WeatherAction.OnClickUseCurrentLocationNo -> onClickUseCurrentLocationNo()
            WeatherAction.OnClickUseCurrentLocationYes -> onClickUseCurrentLocationYes()
            WeatherAction.OnDismissLocationRationale -> onDismissLocationRationale()
            is WeatherAction.SubmitLocationPermissionInfo ->
                submitLocationPermissionInfo(
                    showLocationRationale = action.showLocationRationale,
                    isPermissionGranted = action.isPermissionGranted,
                )
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

    private fun onDismissLocationRationale() {
        _state.update { it.copy(showLocationRationale = false) }
    }

    private fun submitLocationPermissionInfo(
        showLocationRationale: Boolean,
        isPermissionGranted: Boolean,
    ) {
        _state.update { it.copy(showLocationRationale = showLocationRationale) }

        hasLocationPermission.value = isPermissionGranted
    }

    private fun initStateObservers() {
        observeUserLocation()
        observeWeatherForecast()
    }

    private fun observeUserLocation() {
        currentUserLocation
            .onEach { location ->
                location?.let { loadWeatherData(location) }
            }.launchIn(viewModelScope)
    }

    private fun observeWeatherForecast() {
        weatherRepository
            .weatherForecastObservable
            .onEach { forecast ->
                _state.update { it.copy(forecast = forecast) }
            }.launchIn(viewModelScope)
    }

    private fun loadWeatherData(location: Location) {
        Timber.tag("WeatherViewModel").d("location: $location")
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            cacheWeatherForecast(location.lat, location.lon)
                .onSuccess {
                    _state.update { it.copy(isError = false, loading = false) }
                    _event.send(WeatherEvent.ShowMessage(uiText = UiText.StringResource(R.string.you_are_seeing_the_latest_forecast)))
                }.onError {
                    _state.update { it.copy(isError = true, loading = false) }
                    _event.send(WeatherEvent.ShowMessage(uiText = UiText.StringResource(R.string.unable_to_get_latest_forecast)))
                }
        }
    }
}