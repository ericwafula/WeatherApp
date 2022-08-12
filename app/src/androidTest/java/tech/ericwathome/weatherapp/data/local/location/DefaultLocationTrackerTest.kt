package tech.ericwathome.weatherapp.data.local.location

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.common.truth.Truth.assertThat
//import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import java.util.jar.Manifest
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)

class DefaultLocationTrackerTest : LocationTracker {

    override suspend fun getCurrentLocation(): Location? {
        return Location(
            "custom-location-provider"
        )
    }

    @Test
    fun hasFineAccessLocationPermission_returnsTrue() {
        val hasLocationPermission = ContextCompat.checkSelfPermission(
            ApplicationProvider.getApplicationContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        assertThat(hasLocationPermission).isEqualTo(true)
    }

    @Test
    fun checkIfGpsIsEnabled_returnsTrue() {
        val locationManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        assertThat(isGpsEnabled).isTrue()
    }

    @Test
    fun checkIfHasLocationCoarsePermission_returnsTrue() {
        val hasLocationCoarsePermission = ContextCompat.checkSelfPermission(
            ApplicationProvider.getApplicationContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        assertThat(hasLocationCoarsePermission).isTrue()
    }
}