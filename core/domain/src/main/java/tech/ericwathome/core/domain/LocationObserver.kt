package tech.ericwathome.core.domain

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun getLiveLocation(interval: Long): Flow<Location>
}