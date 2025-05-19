package tech.ericwathome.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import tech.ericwathome.weatherapp.navigation.routes.WeatherScreen
import tech.ericwathome.weatherapp.navigation.routes.weatherScreen

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