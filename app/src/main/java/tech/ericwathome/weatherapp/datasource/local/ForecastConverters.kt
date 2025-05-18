package tech.ericwathome.weatherapp.datasource.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import tech.ericwathome.core.domain.model.City
import tech.ericwathome.core.domain.model.ForecastItem

class ForecastConverters {
    @TypeConverter
    fun fromForecastItemList(items: List<ForecastItem>): String = Json.encodeToString(items)

    @TypeConverter
    fun toForecastItemList(json: String): List<ForecastItem> = Json.decodeFromString(json)

    @TypeConverter
    fun fromCity(city: City) = Json.encodeToString(city)

    @TypeConverter
    fun toCity(json: String): City = Json.decodeFromString(json)
}