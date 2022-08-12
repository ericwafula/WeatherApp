package tech.ericwathome.weatherapp.data.local.location

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
//import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import java.util.jar.Manifest
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)

class DefaultLocationTrackerTest : LocationTracker {

    override suspend fun getCurrentLocation(): Location? {
        TODO("Not yet implemented")
    }
}