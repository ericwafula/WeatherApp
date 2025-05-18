package tech.ericwathome.weatherapp.di

import androidx.room.Room
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tech.ericwathome.core.domain.ConnectionObserver
import tech.ericwathome.core.domain.LocationObserver
import tech.ericwathome.core.domain.weather.LocalWeatherDataSource
import tech.ericwathome.core.domain.weather.RemoteWeatherDatasource
import tech.ericwathome.core.domain.weather.WeatherRepository
import tech.ericwathome.weatherapp.data.DefaultWeatherRepository
import tech.ericwathome.weatherapp.data.network.HttpClientFactory
import tech.ericwathome.weatherapp.data.util.DefaultDispatcherProvider
import tech.ericwathome.weatherapp.datasource.local.WeatherAppDatabase
import tech.ericwathome.weatherapp.datasource.local.source.weather.ForecastDao
import tech.ericwathome.weatherapp.datasource.local.source.weather.RoomLocalWeatherDatasource
import tech.ericwathome.weatherapp.datasource.remote.AndroidLocationObserver
import tech.ericwathome.weatherapp.datasource.remote.DefaultConnectionObserver
import tech.ericwathome.weatherapp.datasource.remote.weather.KtorRemoteWeatherDatasource
import tech.ericwathome.weatherapp.domain.util.DispatcherProvider

val dataModule =
    module {
        single<HttpClient> { HttpClientFactory.create() }
        singleOf(::DefaultDispatcherProvider).bind<DispatcherProvider>()
        singleOf(::AndroidLocationObserver).bind<LocationObserver>()
        singleOf(::DefaultConnectionObserver).bind<ConnectionObserver>()
        single<WeatherAppDatabase> {
            Room.databaseBuilder(
                context = androidApplication(),
                klass = WeatherAppDatabase::class.java,
                name = "weatherapp.db",
            ).build()
        }
        single<ForecastDao> { get<WeatherAppDatabase>().forecastDao() }
        singleOf(::RoomLocalWeatherDatasource).bind<LocalWeatherDataSource>()
        singleOf(::KtorRemoteWeatherDatasource).bind<RemoteWeatherDatasource>()
        singleOf(::DefaultWeatherRepository).bind<WeatherRepository>()
    }