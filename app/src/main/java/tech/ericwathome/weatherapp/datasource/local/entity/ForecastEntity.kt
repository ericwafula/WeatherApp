package tech.ericwathome.weatherapp.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.ericwathome.core.domain.model.City
import tech.ericwathome.core.domain.model.ForecastItem

@Entity
data class ForecastEntity(
    @PrimaryKey
    val id: Long = 1,
    val list: List<ForecastItem>,
    val dailySummary: String,
    val city: City,
)