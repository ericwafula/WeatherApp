package tech.ericwathome.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.ericwathome.weatherapp.data.remote.repository.WeatherRepository
import tech.ericwathome.weatherapp.data.remote.repository.WeatherRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(
        repositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

}