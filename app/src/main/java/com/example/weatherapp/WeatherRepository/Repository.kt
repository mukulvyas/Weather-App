package com.example.weatherapp.WeatherRepository

import android.util.Log
import com.example.weatherapp.WeatherData.WeatherDataOrException
import com.example.weatherapp.WeatherData.WeatherDatabaseDao
import com.example.weatherapp.WeatherModel.DayTable
import com.example.weatherapp.WeatherModel.WeatherData
import com.example.weatherapp.WeatherNetwork.WeatherAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(private val weatherAPI: WeatherAPI, private val weatherDatabaseDao: WeatherDatabaseDao) {
    private val weatherData= WeatherDataOrException<WeatherData, Boolean, Exception>()
    suspend fun getWeatherData(date: String): WeatherDataOrException<WeatherData, Boolean, Exception> {
        try {
            weatherData.data = weatherAPI.getAllWeatherDetails(date)
            weatherData.loading= true
            if(weatherData.data.toString().isNotEmpty()) weatherData.loading = false

        } catch (e: Exception) {
            weatherData.exception = e
            Log.d("Exception","getWeatherData: ${weatherData.exception!!.localizedMessage}")
        }
        return weatherData
}

    suspend fun addWeather(day: DayTable) = weatherDatabaseDao.insert(day)


    suspend fun updateWeather(day: DayTable) = weatherDatabaseDao.update(day)

    suspend fun deleteWeather(day: DayTable) = weatherDatabaseDao.deleteWeather(day)

    suspend fun deleteAllWeather() =  weatherDatabaseDao.deleteAll()

    suspend fun getAllWeather() : Flow<List<DayTable>> = weatherDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()


}

class WeatherRepository @Inject constructor(private val weatherDatabaseDao: WeatherDatabaseDao) {
    //val allWeatherData = weatherDatabaseDao.getAll()

    suspend fun addWeather(day: DayTable) = weatherDatabaseDao.insert(day)


    suspend fun updateWeather(day: DayTable) = weatherDatabaseDao.update(day)

    suspend fun deleteWeather(day: DayTable) = weatherDatabaseDao.deleteWeather(day)

    suspend fun deleteAllWeather() =  weatherDatabaseDao.deleteAll()

    suspend fun getAllWeather() : Flow<List<DayTable>> = weatherDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
}


