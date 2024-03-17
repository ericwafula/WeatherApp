package tech.ericwathome.core_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.ericwathome.core_data.repository.DefaultNetworkStatusManager
import tech.ericwathome.core_domain.repository.NetworkStatusManager
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
abstract class CoreDataRepositoryModule {

    @[Binds Singleton]
    abstract fun bindNetworkStatusManager(defaultNetworkStatusManager: DefaultNetworkStatusManager): NetworkStatusManager

}