package com.example.weatherapp.WeatherScreens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherData.WeatherDataOrException
import com.example.weatherapp.WeatherModel.WeatherData
import com.example.weatherapp.WeatherRepository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    val data: MutableState<WeatherDataOrException<WeatherData, Boolean, Exception>> = mutableStateOf(
        WeatherDataOrException(
           null,
            false,
            null
        )
    )

    init {
        getAllWeatherDetails() // get all weather details
    }

    fun getAllWeatherDetails(){

        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getWeatherData("2024-01-13")
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }

    }


}