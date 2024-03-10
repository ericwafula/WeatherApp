package tech.ericwathome.core_domain.repository

import kotlinx.coroutines.flow.Flow
import tech.ericwathome.core_domain.utils.NetworkStatus

interface NetworkStatusManager {
    fun getCurrentStatus(): Flow<NetworkStatus>
}