package com.example.weatherapp.WeatherData

data class WeatherDataOrException<T,Boolean,E: Exception> (
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: E? = null
)