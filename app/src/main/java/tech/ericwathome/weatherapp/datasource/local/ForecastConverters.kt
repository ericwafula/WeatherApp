package tech.ericwathome.weatherapp.datasource.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import tech.ericwathome.weatherapp.datasource.local.entity.CityEntity
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastItemEntity

class ForecastConverters {
    @TypeConverter
    fun fromForecastItemList(items: List<ForecastItemEntity>): String = Json.encodeToString(items)

    @TypeConverter
    fun toForecastItemList(json: String): List<ForecastItemEntity> = Json.decodeFromString(json)

    @TypeConverter
    fun fromCity(city: CityEntity) = Json.encodeToString(city)

    @TypeConverter
    fun toCity(json: String): CityEntity = Json.decodeFromString(json)
}