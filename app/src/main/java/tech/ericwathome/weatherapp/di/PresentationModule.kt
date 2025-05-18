package tech.ericwathome.weatherapp.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import tech.ericwathome.weatherapp.domain.usecase.CacheCityData
import tech.ericwathome.weatherapp.domain.usecase.CacheWeatherForecast
import tech.ericwathome.weatherapp.presentation.searchlocation.SearchLocationViewModel
import tech.ericwathome.weatherapp.presentation.weatherscreen.WeatherViewModel

val presentationModule =
    module {
        viewModelOf(::WeatherViewModel)
        singleOf(::CacheWeatherForecast)
        singleOf(::CacheCityData)
        viewModelOf(::SearchLocationViewModel)
    }