package tech.ericwathome.core.domain.weather

import kotlinx.coroutines.flow.Flow
import tech.ericwathome.core.domain.model.CityData
import tech.ericwathome.core.domain.model.Forecast

interface WeatherRepository {
    val weatherForecastObservable: Flow<Forecast?>
    val cityDataObservable: Flow<List<CityData>?>
}