package tech.ericwathome.weatherapp.presentation.searchlocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.ericwathome.core.domain.util.onError
import tech.ericwathome.core.domain.util.onSuccess
import tech.ericwathome.core.domain.weather.WeatherRepository
import tech.ericwathome.core.ui.UiText
import tech.ericwathome.core.ui.extract
import tech.ericwathome.weatherapp.R
import tech.ericwathome.weatherapp.domain.usecase.CacheCityData
import tech.ericwathome.weatherapp.presentation.searchlocation.mappers.toPresentation

@OptIn(FlowPreview::class)
class SearchLocationViewModel(
    private val weatherRepository: WeatherRepository,
    private val cacheCityData: CacheCityData,
) : ViewModel() {
    private val _event = Channel<SearchLocationEvent>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(SearchLocationState())
    val state =
        _state
            .onStart {
                initState()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchLocationState(),
            )

    fun onAction(action: SearchLocationAction) {
        when (action) {
            SearchLocationAction.OnClickContinue -> onClickContinue()
            is SearchLocationAction.OnEnterSearchQuery -> onEnterSearchQuery(action.query)
            is SearchLocationAction.OnSelectLocation -> onSelectLocation(action.index)
            SearchLocationAction.OnClickSearchIcon -> filterCityData(state.value.searchQuery)
            else -> Unit
        }
    }

    private fun onClickContinue() {
        viewModelScope.launch {
            val selectedCityData = state.value.filteredCityData.find { it.isSelected }

            selectedCityData?.let {
                _event.send(SearchLocationEvent.OnSelectCityData(selectedCityData))
            }
        }
    }

    private fun onEnterSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    private fun onSelectLocation(index: Int) {
        _state.update {
            it.copy(
                filteredCityData =
                    state.value.filteredCityData.mapIndexed { cIndex, cityData ->
                        if (cIndex == index) {
                            cityData.copy(isSelected = true)
                        } else {
                            cityData.copy(isSelected = false)
                        }
                    },
            )
        }
    }

    private fun initState() {
        observeCityData()
        observeSearchQuery()
        fetchCityData()
    }

    private fun observeCityData() {
        weatherRepository
            .cityDataObservable
            .filterNotNull()
            .onEach { cityData ->
                _state.update {
                    it.copy(
                        cityData = cityData.map { it.toPresentation() },
                        filteredCityData = cityData.map { it.toPresentation() },
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            state
                .debounce(500)
                .extract { it.searchQuery }
                .onEach { _state.update { it.copy(isLoading = true) } }
                .collectLatest { query ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            filteredCityData = filterCityData(query),
                        )
                    }
                }
        }
    }

    private fun filterCityData(query: String): List<CityDataState> {
        return state.value.cityData.filter {
            it.name.contains(other = query, ignoreCase = true) ||
                it.country.contains(other = query, ignoreCase = true)
        }
    }

    private fun fetchCityData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            cacheCityData()
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isError = false) }
                    _event.send(SearchLocationEvent.ShowToast(message = UiText.StringResource(R.string.you_are_seeing_the_latest_city_data)))
                }
                .onError {
                    _state.update { it.copy(isLoading = false, isError = true) }
                    _event.send(SearchLocationEvent.ShowToast(message = UiText.StringResource(R.string.unable_to_fetch_latest_city_data)))
                }
        }
    }
}