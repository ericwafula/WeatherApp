package tech.ericwathome.weatherapp.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import tech.ericwathome.weatherapp.datasource.local.entity.ForecastEntity

@Dao
interface ForecastDao {
    @Upsert
    suspend fun upsertForecast(forecast: ForecastEntity)

    @Query("SELECT * FROM ForecastEntity WHERE id = 1")
    suspend fun getForecast(): ForecastEntity?
}