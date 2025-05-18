package tech.ericwathome.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import tech.ericwathome.weatherapp.navigation.screens.WeatherScreen
import tech.ericwathome.weatherapp.navigation.screens.searchLocationScreen
import tech.ericwathome.weatherapp.navigation.screens.weatherScreen

@Composable
fun RootNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreen,
    ) {
        weatherScreen(navController)
        searchLocationScreen(navController)
    }
}