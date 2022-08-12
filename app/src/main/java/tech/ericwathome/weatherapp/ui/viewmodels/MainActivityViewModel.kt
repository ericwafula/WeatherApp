package tech.ericwathome.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.ericwathome.weatherapp.data.model.WeatherEvent
import tech.ericwathome.weatherapp.data.remote.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    private val _weatherInfo = MutableStateFlow<WeatherEvent>(WeatherEvent.Empty)
    private val weatherInfo = _weatherInfo.asStateFlow()

    fun getCurrentWeatherInfo() {

    }

}