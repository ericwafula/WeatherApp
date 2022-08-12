package tech.ericwathome.weatherapp.data.local.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}