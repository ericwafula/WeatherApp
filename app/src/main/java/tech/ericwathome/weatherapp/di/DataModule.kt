package tech.ericwathome.weatherapp.di

import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tech.ericwathome.core.domain.LocationObserver
import tech.ericwathome.weatherapp.data.network.HttpClientFactory
import tech.ericwathome.weatherapp.data.util.DefaultDispatcherProvider
import tech.ericwathome.weatherapp.datasource.remote.AndroidLocationObserver
import tech.ericwathome.weatherapp.domain.util.DispatcherProvider

val dataModule = module {
    single<HttpClient> { HttpClientFactory.create() }
    singleOf(::DefaultDispatcherProvider).bind<DispatcherProvider>()
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}