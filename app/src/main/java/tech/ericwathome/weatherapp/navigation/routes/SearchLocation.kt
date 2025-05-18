package tech.ericwathome.weatherapp.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import tech.ericwathome.weatherapp.presentation.searchlocation.SearchLocationScreen

@Serializable
object SearchLocationScreen

fun NavGraphBuilder.searchLocationScreen(navController: NavHostController) {
    composable<SearchLocationScreen> {
        SearchLocationScreen(
            onNavigateUp = { navController.popBackStack() },
            onNavigateUpWithCityData = { cityData ->
                navController.apply {
                    previousBackStackEntry?.savedStateHandle?.set("cityData", cityData)
                    popBackStack()
                }
            },
        )
    }
}