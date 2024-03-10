package tech.ericwathome.core_domain.utils

sealed class NetworkStatus {
    data object Connected : NetworkStatus()
    data object Disconnected : NetworkStatus()
    data object Disconnecting : NetworkStatus()
}