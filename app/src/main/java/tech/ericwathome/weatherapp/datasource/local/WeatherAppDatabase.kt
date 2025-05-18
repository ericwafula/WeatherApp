package tech.ericwathome.weatherapp.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.ericwathome.weatherapp.datasource.local.entity.CityDataEntity
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastEntity
import tech.ericwathome.weatherapp.datasource.local.source.weather.CityDataDao
import tech.ericwathome.weatherapp.datasource.local.source.weather.ForecastDao

@Database(
    entities = [ForecastEntity::class, CityDataEntity::class],
    version = 1,
)
@TypeConverters(ForecastConverters::class)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao

    abstract fun cityDataDao(): CityDataDao
}