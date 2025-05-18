package tech.ericwathome.weatherapp.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class CityDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val country: String,
    val name: String,
    val lat: Double,
    val lon: Double,
)
