package tech.ericwathome.weatherapp.presentation.searchlocation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import tech.ericwathome.core.ui.CollectOneTimeEvent
import tech.ericwathome.designsystem.WeatherAppTheme
import tech.ericwathome.designsystem.assets.AppIcons
import tech.ericwathome.designsystem.components.WeatherAppFilledButton
import tech.ericwathome.designsystem.components.WeatherAppTextField
import tech.ericwathome.designsystem.components.WeatherAppToggleChip
import tech.ericwathome.designsystem.components.WeatherAppToolbarLayout

@Composable
fun SearchLocationScreen(
    onNavigateUp: () -> Unit,
    onNavigateUpWithCityData: (CityDataState) -> Unit,
    viewModel: SearchLocationViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectOneTimeEvent(viewModel.event) { event ->
        when (event) {
            is SearchLocationEvent.OnSelectCityData -> onNavigateUpWithCityData(event.cityData)
            is SearchLocationEvent.ShowToast -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    SearchLocationScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                SearchLocationAction.OnClickCloseIcon -> onNavigateUp()
                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
private fun SearchLocationScreenContent(
    state: SearchLocationState,
    onAction: (SearchLocationAction) -> Unit,
) {
    WeatherAppToolbarLayout(
        title = "Search location",
        actions = {
            IconButton(
                onClick = { onAction(SearchLocationAction.OnClickCloseIcon) },
            ) {
                Icon(
                    imageVector = AppIcons.CloseOutlined,
                    contentDescription = "Close icon",
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column {
                Box(
                    modifier = Modifier.padding(24.dp),
                ) {
                    WeatherAppTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.searchQuery,
                        onValueChange = { onAction(SearchLocationAction.OnEnterSearchQuery(it)) },
                        hint = "Enter city name...",
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Search,
                            ),
                        keyboardActions =
                            KeyboardActions(
                                onSearch = {
                                    onAction(SearchLocationAction.OnClickSearchIcon)
                                },
                            ),
                        leadingIcon = AppIcons.SearchOutlined,
                    )
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
                    text = "Results...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                    ) {
                        itemsIndexed(state.filteredCityData) { index, cityData ->
                            WeatherAppToggleChip(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onAction(SearchLocationAction.OnSelectLocation(index)) },
                                isSelected = cityData.isSelected,
                                text = "${cityData.name.lowercase().replaceFirstChar { it.uppercase() }}, ${cityData.country.uppercase()}",
                            )
                        }
                    }
                }
            }
            Box(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(18.dp),
            ) {
                WeatherAppFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Continue",
                    onClick = { onAction(SearchLocationAction.OnClickContinue) },
                    isEnabled = !state.isLoading,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchLocationScreenPreview() {
    WeatherAppTheme {
        SearchLocationScreenContent(
            state =
                SearchLocationState(
                    filteredCityData =
                        listOf(
                            CityDataState(
                                country = "KE",
                                name = "Nairobi",
                                lat = -1.28333,
                                lon = 36.81667,
                            ),
                            CityDataState(
                                country = "KE",
                                name = "Mombasa",
                                lat = -1.28333,
                                lon = 36.81667,
                            ),
                            CityDataState(
                                country = "KE",
                                name = "Nakuru",
                                lat = -1.28333,
                                lon = 36.81667,
                            ),
                        ),
                    isLoading = true,
                ),
            onAction = { },
        )
    }
}