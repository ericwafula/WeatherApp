package tech.ericwathome.weatherapp.data.model

data class Timeline(
    val endTime: String,
    val intervals: List<Interval>,
    val startTime: String,
    val timestep: String
)