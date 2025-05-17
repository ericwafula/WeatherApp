package tech.ericwathome.weatherapp.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import tech.ericwathome.weatherapp.data.network.HttpClientFactory

val dataModule = module {
    single<HttpClient> { HttpClientFactory.create() }
}