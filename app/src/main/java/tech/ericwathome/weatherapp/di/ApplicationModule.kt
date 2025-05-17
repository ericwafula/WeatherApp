package tech.ericwathome.weatherapp.di

import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import tech.ericwathome.weatherapp.WeatherApp

val applicationModule =
    module {
        single<CoroutineScope> { (androidApplication() as WeatherApp).applicationScope }
    }