package com.example.weatherapp.WeatherData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.WeatherModel.DayTable

@Database(entities = [DayTable::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase(){
    abstract fun weatherDao(): WeatherDatabaseDao

}