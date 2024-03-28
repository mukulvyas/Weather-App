package com.example.weatherapp.WeatherData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.WeatherModel.DayTable
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDatabaseDao {

    @Query("SELECT * FROM Day_tbl")
    fun getAll():
            Flow<List<DayTable>>

    @Query("SELECT * FROM Day_tbl WHERE datetime = :datetime")
    suspend fun getDay(datetime: String): DayTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(day: DayTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(day: DayTable)

    @Query("DELETE FROM Day_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteWeather(day: DayTable)
}
