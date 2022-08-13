package tech.ericwathome.weatherapp.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import tech.ericwathome.weatherapp.R
import tech.ericwathome.weatherapp.databinding.ActivityMainBinding
import tech.ericwathome.weatherapp.ui.viewmodels.MainActivityViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.simpleName
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val viewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerPermissions()
        getLocationData()
    }

    private fun registerPermissions() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            viewModel.getCurrentWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    private fun getLocationData() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { weatherState ->
                    weatherState.weatherInfo?.let {
                        val currentTemp = it.data.timelines[0].intervals[0].values.temperature
                        binding.tvTemp.text = "$currentTemp"
                        binding.tvLocation.text = viewModel.getCurrentAddress(this@MainActivity)
                    }
                }
        }
    }
}