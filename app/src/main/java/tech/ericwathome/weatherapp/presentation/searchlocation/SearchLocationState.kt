package tech.ericwathome.weatherapp.presentation.searchlocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class SearchLocationState(
    val isLoading: Boolean = false,
    val cityData: List<CityDataState> = emptyList(),
    val isError: Boolean = false,
    val searchQuery: String = "",
    val filteredCityData: List<CityDataState> = emptyList(),
)

@Serializable
@Parcelize
data class CityDataState(
    val country: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val isSelected: Boolean = false,
) : Parcelable