package tech.ericwathome.weatherapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.ericwathome.weatherapp.data.local.location.LocationTracker
import tech.ericwathome.weatherapp.data.model.WeatherState
import tech.ericwathome.weatherapp.data.remote.repository.WeatherRepository
import tech.ericwathome.weatherapp.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun getCurrentWeatherInfo() {
        viewModelScope.launch {
            _state.value = WeatherState(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                Log.d("TAG", "getCurrentWeatherInfo: $location")
                when (val result = repository.getCurrentWeatherInfo(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        _state.value = WeatherState(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = WeatherState(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                _state.value = WeatherState(
                    isLoading = false,
                    error = "Couldn't receive location data. Make sure to grant location permissions and enable GPS."
                )
            }
        }
    }

}