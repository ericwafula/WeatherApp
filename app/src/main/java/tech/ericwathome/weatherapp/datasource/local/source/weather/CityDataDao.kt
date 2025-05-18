package tech.ericwathome.weatherapp.datasource.local.source.weather

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import tech.ericwathome.weatherapp.datasource.local.entity.CityDataEntity

@Dao
interface CityDataDao {
    @Upsert
    suspend fun upsertCityData(data: List<CityDataEntity>)

    @Query("SELECT * FROM citydataentity")
    fun observeCityData(): Flow<List<CityDataEntity>?>
}