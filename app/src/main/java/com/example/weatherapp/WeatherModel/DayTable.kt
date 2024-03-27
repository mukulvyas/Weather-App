package com.example.weatherapp.WeatherModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Day_tbl")
data class DayTable(
    @PrimaryKey
    val datetime: String,

    @ColumnInfo(name = "temperature")
    val temp: Double,

    @ColumnInfo(name = "max_temperature")
    val tempmax: Double,

    @ColumnInfo(name = "min_temperature")
    val tempmin: Double
)
