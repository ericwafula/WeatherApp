package tech.ericwathome.weatherapp.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastEntity

@Database(entities = [ForecastEntity::class], version = 1)
@TypeConverters(ForecastConverters::class)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}