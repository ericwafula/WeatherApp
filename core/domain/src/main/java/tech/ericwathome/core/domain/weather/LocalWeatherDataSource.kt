package tech.ericwathome.core.domain.weather

import kotlinx.coroutines.flow.Flow
import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.core.domain.model.Forecast
import tech.ericwathome.core.domain.util.EmptyResult
import tech.ericwathome.weatherapp.domain.util.DataError

interface LocalWeatherDataSource {
    val weatherForecastObservable: Flow<Forecast?>
    val cityDataObservable: Flow<List<CityData>?>

    suspend fun upsertWeatherForecast(forecast: Forecast): EmptyResult<DataError.Local>

    suspend fun getWeatherForecast(): Forecast?

    suspend fun upsertCityData(data: List<CityData>): EmptyResult<DataError.Local>
}