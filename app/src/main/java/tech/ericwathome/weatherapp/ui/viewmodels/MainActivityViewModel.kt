package tech.ericwathome.weatherapp.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import tech.ericwathome.weatherapp.data.remote.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(repository: WeatherRepository) {
}