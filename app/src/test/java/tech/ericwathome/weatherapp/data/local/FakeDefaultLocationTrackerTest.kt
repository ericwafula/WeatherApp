package tech.ericwathome.weatherapp.data.local

import android.location.Location
import tech.ericwathome.weatherapp.data.local.location.LocationTracker

class DefaultLocationTrackerTest: LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        return Location(
            "custom-location-provider"
        )
    }
}