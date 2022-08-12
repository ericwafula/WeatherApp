package tech.ericwathome.weatherapp.data.remote.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import tech.ericwathome.weatherapp.data.model.*
import tech.ericwathome.weatherapp.util.Resource

@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest: WeatherRepository {

    private lateinit var weatherInfo: WeatherInfo

    @Before
    fun setup() {
        weatherInfo = setupWeatherInfoObject()
    }

    private fun setupWeatherInfoObject(): WeatherInfo {
        val values = Values(-50.0)
        val interval = Interval("2022-08-12T03:00:00Z", values)
        val timeline =
            Timeline("1d", listOf(interval), "2022-08-12T03:00:00Z", "2022-08-26T03:00:00Z")
        val data = Data(listOf(timeline))
        return WeatherInfo(data)
    }

    override suspend fun getCurrentWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return Resource(weatherInfo, null)
    }

    @Test
    fun `fetch start-time details, returns true`() {
        var resource: Resource<WeatherInfo>? = null
        runTest {
            resource = getCurrentWeatherInfo(-73.98529171943665, 40.75872069597532)
        }
        assertEquals("2022-08-12T03:00:00Z", resource?.data?.data?.timelines?.get(0)?.startTime)
    }
}