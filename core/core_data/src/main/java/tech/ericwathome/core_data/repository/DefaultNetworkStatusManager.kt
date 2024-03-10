package tech.ericwathome.core_data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import tech.ericwathome.core_domain.repository.NetworkStatusManager
import tech.ericwathome.core_domain.utils.NetworkStatus

class DefaultNetworkStatusManager(
    context: Context
) : NetworkStatusManager {

    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    override fun getCurrentStatus(): Flow<NetworkStatus> {
        return callbackFlow {
            val networkCallback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.Connected)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    trySend(NetworkStatus.Disconnecting)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.Disconnected)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(NetworkStatus.Disconnected)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(networkCallback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }
    }

}