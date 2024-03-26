package com.example.weatherapp.WeatherRepository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.weatherapp.WeatherData.WeatherDataOrException
import com.example.weatherapp.WeatherModel.WeatherData
import com.example.weatherapp.WeatherNetwork.WeatherAPI
import javax.inject.Inject

class Repository @Inject constructor(private val weatherAPI: WeatherAPI) {
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

}