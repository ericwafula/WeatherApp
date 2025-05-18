package tech.ericwathome.weatherapp.presentation.util

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import tech.ericwathome.core.ui.hasGrantedPermission

fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun Context.hasLocationPermissions(): Boolean {
    return hasGrantedPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}