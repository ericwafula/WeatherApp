package tech.ericwathome.weatherapp.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SearchLocationScreen

fun NavGraphBuilder.searchLocationScreen(navController: NavHostController) {
    composable<SearchLocationScreen> {
    }
}