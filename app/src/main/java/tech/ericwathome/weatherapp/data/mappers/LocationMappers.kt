package tech.ericwathome.core.remote.mappers

import android.location.Location

fun Location.toDomain(): tech.ericwathome.core.domain.model.Location {
    return tech.ericwathome.core.domain.model.Location(
        lat = latitude,
        lon = longitude,
    )
}