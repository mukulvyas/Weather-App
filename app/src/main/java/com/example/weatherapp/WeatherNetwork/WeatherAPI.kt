package com.example.weatherapp.WeatherNetwork

import com.example.weatherapp.WeatherModel.Day
import com.example.weatherapp.WeatherModel.WeatherData
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface WeatherAPI {

    @GET("New%20Delhi/{date}?unitGroup=uk&elements=datetime%2Cname%2Caddress%2Clatitude%2Clongitude%2Ctempmax%2Ctempmin%2Ctemp&include=days&key=5JAVTMC4RRUY2MDZNTQJB9VRV&contentType=json")

    suspend fun getAllWeatherDetails(@Path("date") date: String): WeatherData
}