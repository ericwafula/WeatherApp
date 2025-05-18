package tech.ericwathome.core.domain

import kotlinx.coroutines.flow.Flow
import tech.ericwathome.core.domain.model.Location

interface LocationObserver {
    fun getLiveLocation(interval: Long): Flow<Location>
}