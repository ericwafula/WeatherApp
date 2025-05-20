package tech.ericwathome.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import tech.ericwathome.weatherapp.presentation.navigation.routes.WeatherScreen
import tech.ericwathome.weatherapp.presentation.navigation.routes.weatherScreen

@Composable
fun RootNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreen,
    ) {
        weatherScreen()
    }
}