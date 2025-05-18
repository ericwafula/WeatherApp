package tech.ericwathome.weatherapp.presentation.weatherscreen

import android.Manifest
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import org.koin.androidx.compose.koinViewModel
import tech.ericwathome.core.domain.model.City
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.model.ForecastItem
import tech.ericwathome.core.domain.model.Main
import tech.ericwathome.core.domain.model.Weather
import tech.ericwathome.core.domain.model.Wind
import tech.ericwathome.core.domain.util.DateUtils
import tech.ericwathome.core.domain.util.DateUtils.toFullDayMonthDate
import tech.ericwathome.core.domain.util.kelvinToFormattedCelsius
import tech.ericwathome.core.ui.CollectOneTimeEvent
import tech.ericwathome.designsystem.WeatherAppTheme
import tech.ericwathome.designsystem.assets.AppIcons
import tech.ericwathome.designsystem.components.WeatherAppDialog
import tech.ericwathome.designsystem.components.WeatherAppFilledButton
import tech.ericwathome.designsystem.components.WeatherAppOutlinedButton
import tech.ericwathome.designsystem.components.WeatherAppToolbarLayout
import tech.ericwathome.designsystem.utils.ImageUtils
import tech.ericwathome.designsystem.utils.LocalTextUtils
import tech.ericwathome.designsystem.utils.shimmerEffect
import tech.ericwathome.weatherapp.presentation.searchlocation.CityDataState
import tech.ericwathome.weatherapp.presentation.util.hasLocationPermissions
import tech.ericwathome.weatherapp.presentation.util.shouldShowLocationPermissionRationale
import java.time.LocalDateTime
import kotlin.math.ceil

@Composable
fun WeatherScreen(
    onNavigateToSearchLocation: () -> Unit,
    cityDataState: CityDataState?,
    viewModel: WeatherViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectOneTimeEvent(viewModel.event) { event ->
        when (event) {
            is WeatherEvent.ShowMessage -> {
                Toast.makeText(
                    context,
                    event.uiText.asString(context),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    LaunchedEffect(true) {
        cityDataState?.let {
            viewModel.initCityData(cityDataState)
        }
    }

    WeatherScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                WeatherAction.OnClickSearchIcon -> onNavigateToSearchLocation()
                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
fun WeatherScreenContent(
    state: WeatherState,
    onAction: (WeatherAction) -> Unit,
) {
    val context = LocalContext.current
    val activity = remember { context as? ComponentActivity }
    val currentDate = remember { LocalDateTime.now().toLocalDate().toFullDayMonthDate() }
    val currentForecast = state.forecast?.list?.firstOrNull()
    val windSpeed = ceil(currentForecast?.wind?.speed ?: 0.0).toInt()
    val visibilityInKm = (currentForecast?.visibility ?: 0) / 1000
    val formattedTemp = currentForecast?.main?.temp?.kelvinToFormattedCelsius()?.removeRange(0, 1) ?: "0"

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            val showLocationRationale = activity?.shouldShowLocationPermissionRationale() == true

            onAction(
                WeatherAction.SubmitLocationPermissionInfo(
                    showLocationRationale = showLocationRationale,
                    isPermissionGranted = isGranted,
                ),
            )
        }

    LaunchedEffect(true) {
        val showLocationRationale = activity?.shouldShowLocationPermissionRationale() == true
        val hasLocationPermission = context.hasLocationPermissions()

        onAction(
            WeatherAction.SubmitLocationPermissionInfo(
                showLocationRationale = showLocationRationale,
                isPermissionGranted = hasLocationPermission,
            ),
        )

        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    WeatherAppToolbarLayout(
        title = "${state.forecast?.city?.name ?: "Location not found"}, ${state.forecast?.city?.country ?: ""}",
        actions = {
            IconButton(
                onClick = { onAction(WeatherAction.OnClickSearchIcon) },
            ) {
                Icon(
                    imageVector = AppIcons.SearchOutlined,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        },
    ) {
        if (!state.loading && state.forecast == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "No weather data found!",
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                )
            }
        }
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(40.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                ) {
                    Text(
                        text = currentDate,
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSecondary,
                            ),
                    )
                }
            }
            Text(
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .then(
                            if (state.loading) {
                                Modifier.shimmerEffect()
                            } else {
                                Modifier
                            },
                        ),
                text = currentForecast?.weather?.firstOrNull()?.main ?: "Nothing found",
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    ),
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                Text(
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .then(
                                if (state.loading) {
                                    Modifier.shimmerEffect()
                                } else {
                                    Modifier
                                },
                            ),
                    text = formattedTemp,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = with(LocalTextUtils.current) { 120.sp.fixedSize },
                            fontWeight = FontWeight.Bold,
                        ),
                )
                Spacer(modifier = Modifier.height(48.dp))
                WeatherInfoSection(
                    modifier =
                        Modifier
                            .weight(1f),
                    title = "Daily Summary",
                ) {
                    Text(
                        modifier =
                            Modifier
                                .padding(horizontal = 24.dp)
                                .then(
                                    if (state.loading) {
                                        Modifier.shimmerEffect()
                                    } else {
                                        Modifier
                                    },
                                ),
                        text = state.forecast?.dailySummary ?: "Looks like we have nothing to show you at the moment",
                        style =
                            MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 14.sp,
                            ),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 32.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        WeatherInfoDataColumn(
                            data = "${windSpeed}Km/h",
                            icon = AppIcons.WindOutlined,
                            title = "Wind",
                            isLoading = state.loading,
                        )
                        WeatherInfoDataColumn(
                            data = "${currentForecast?.main?.humidity ?: 0}%",
                            icon = AppIcons.DropletOutlined,
                            title = "Humidity",
                            isLoading = state.loading,
                        )
                        WeatherInfoDataColumn(
                            data = "${visibilityInKm}Km",
                            icon = AppIcons.EyeOutlined,
                            title = "Visibility",
                            isLoading = state.loading,
                        )
                    }
                }
                WeatherInfoSection(
                    modifier =
                        Modifier
                            .heightIn(166.dp)
                            .fillMaxWidth(),
                    title = "Weekly Forecast",
                ) {
                    if (state.forecast?.list.orEmpty().isEmpty() && !state.loading) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                        ) {
                            Text(
                                modifier =
                                    Modifier
                                        .align(Alignment.Center),
                                text = "Nothing found!",
                                style =
                                    MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onBackground,
                                    ),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                    ) {
                        items(state.forecast?.list ?: emptyList()) { forecast ->
                            Column(
                                modifier =
                                    Modifier
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                                            shape = RoundedCornerShape(12.dp),
                                        )
                                        .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    modifier =
                                        Modifier
                                            .then(
                                                if (state.loading) {
                                                    Modifier.shimmerEffect()
                                                } else {
                                                    Modifier
                                                },
                                            ),
                                    text = forecast.main.temp.kelvinToFormattedCelsius().removeRange(0, 1),
                                    style =
                                        MaterialTheme.typography.bodyLarge.copy(
                                            color = MaterialTheme.colorScheme.secondary,
                                            fontWeight = FontWeight.Medium,
                                        ),
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                SubcomposeAsyncImage(
                                    modifier = Modifier.size(24.dp),
                                    model = ImageUtils.createImageRequest(ImageUtils.ImageType.PNG, forecast.weather.firstOrNull()?.icon ?: ""),
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    loading = {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .clip(CircleShape)
                                                    .fillMaxSize()
                                                    .shimmerEffect(),
                                        )
                                    },
                                    error = {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .fillMaxSize()
                                                    .background(MaterialTheme.colorScheme.onSecondary),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Icon(
                                                imageVector = AppIcons.FrownOutlined,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error,
                                            )
                                        }
                                    },
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    modifier =
                                        Modifier
                                            .then(
                                                if (state.loading) {
                                                    Modifier.shimmerEffect()
                                                } else {
                                                    Modifier
                                                },
                                            ),
                                    text = with(DateUtils) { forecast.dt.toDayShotMonth() },
                                    style =
                                        MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Normal,
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.showLocationRationale) {
            WeatherAppDialog(
                title = "Allow location access?",
                description = "We use your location to show weather updates for where you are right now. It helps us keep things accurate and relevant.",
                onDismiss = { /* user should not be able to dismiss the location rationale unless they click deny */ },
                actions = {
                    WeatherAppOutlinedButton(
                        modifier = Modifier.weight(1f),
                        text = "Deny",
                        onClick = { onAction(WeatherAction.OnDismissLocationRationale) },
                    )
                    WeatherAppFilledButton(
                        modifier = Modifier.weight(1f),
                        text = "Allow",
                        onClick = { locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                    )
                },
            )
        }
    }
}

@Composable
private fun WeatherInfoSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Column {
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = title,
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
            Spacer(modifier = Modifier.height(2.dp))
            content()
        }
    }
}

@Composable
private fun WeatherInfoDataColumn(
    isLoading: Boolean = false,
    data: String,
    icon: ImageVector,
    title: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
            )
            Text(
                modifier =
                    Modifier
                        .then(
                            if (isLoading) {
                                Modifier.shimmerEffect()
                            } else {
                                Modifier
                            },
                        ),
                text = data,
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = with(LocalTextUtils.current) { 20.sp.fixedSize },
                        fontWeight = FontWeight.Bold,
                    ),
            )
        }
        Text(
            modifier = Modifier,
            text = title,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    fontSize = with(LocalTextUtils.current) { 14.sp.fixedSize },
                ),
        )
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreenContent(
            state =
                WeatherState(
                    loading = false,
                    forecast =
                        Forecast(
                            list =
                                listOf(
                                    ForecastItem(
                                        dt = 1747580400,
                                        main =
                                            Main(
                                                temp = 293.49,
                                                feelsLike = 293.64,
                                                minTemp = 292.45,
                                                maxTemp = 293.49,
                                                humidity = 79,
                                            ),
                                        weather =
                                            listOf(
                                                Weather(
                                                    id = 500,
                                                    main = "Rain",
                                                    description = "light rain",
                                                    icon = "10d",
                                                ),
                                            ),
                                        wind = Wind(speed = 1.63),
                                        visibility = 9989,
                                        dtTxt = "2025-05-18 15:00:00",
                                    ),
                                ),
                            dailySummary =
                                """
                                Now it feels like +35º, actually +31º.
                                It feels hot because of the direct sun. Today, the temperature is felt in the range from +31º to 27º.
                                """.trimIndent(),
                            city =
                                City(
                                    id = 198442,
                                    name = "Embakasi",
                                    country = "KE",
                                ),
                        ),
                ),
            onAction = { },
        )
    }
}