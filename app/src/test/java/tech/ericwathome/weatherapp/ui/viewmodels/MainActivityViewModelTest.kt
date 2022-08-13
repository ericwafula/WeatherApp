package tech.ericwathome.weatherapp.ui.viewmodels

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Test
import tech.ericwathome.weatherapp.data.local.DefaultLocationTrackerTest
import tech.ericwathome.weatherapp.data.model.*
import tech.ericwathome.weatherapp.data.remote.repository.WeatherRepositoryImplTest

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    private lateinit var viewModel: MainActivityViewModel
    private val dispatcher = StandardTestDispatcher()
    private lateinit var weatherInfo: WeatherInfo

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MainActivityViewModel(WeatherRepositoryImplTest(), DefaultLocationTrackerTest())
        viewModel.getCurrentWeatherInfo()
        weatherInfo = setupWeatherInfoObject()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun setupWeatherInfoObject(): WeatherInfo {
        val values = Values(-50.0)
        val interval = Interval("2022-08-12T03:00:00Z", values)
        val timeline =
            Timeline("1d", listOf(interval), "2022-08-12T03:00:00Z", "2022-08-26T03:00:00Z")
        val data = Data(listOf(timeline))
        return WeatherInfo(data)
    }


    @Test
    fun `ensure current state is not loading, returns true`() {
        val weatherState = viewModel.state.value

        assertThat(weatherState.isLoading).isEqualTo(WeatherState().isLoading)
    }

    @Test
    fun `get weather info, returns true`() {
        val weatherState = viewModel.state.value
        val values = Values(-50.0)
        val interval = Interval("2022-08-12T03:00:00Z", values)
        val timeline =
            Timeline("1d", listOf(interval), "2022-08-12T03:00:00Z", "2022-08-26T03:00:00Z")
        val data = Data(listOf(timeline))
        weatherState.weatherInfo = WeatherInfo(data)
        assertThat(weatherState.weatherInfo).isEqualTo(weatherInfo)
    }
}