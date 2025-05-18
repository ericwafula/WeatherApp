package tech.ericwathome.weatherapp.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import tech.ericwathome.weatherapp.presentation.weatherscreen.WeatherScreen

@Serializable
object WeatherScreen

fun NavGraphBuilder.weatherScreen(navController: NavHostController) {
    composable<WeatherScreen> {
        WeatherScreen(
            onNavigateToSearchLocation = {
                navController.navigate(SearchLocationScreen)
            },
        )
    }
}