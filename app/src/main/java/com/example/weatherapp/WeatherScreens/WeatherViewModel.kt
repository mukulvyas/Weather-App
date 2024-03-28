package com.example.weatherapp.WeatherScreens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherData.WeatherDataOrException
import com.example.weatherapp.WeatherModel.DayTable
import com.example.weatherapp.WeatherModel.WeatherData
import com.example.weatherapp.WeatherRepository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class WeatherViewModel @Inject constructor(private val repository: Repository): ViewModel(){
//    private val _weatherList = MutableStateFlow<List<DayTable>>(emptyList())
//    val weatherList = _weatherList.asStateFlow()
//
//
//    val data: MutableState<WeatherDataOrException<WeatherData, Boolean, Exception>> = mutableStateOf(
//        WeatherDataOrException(
//           null,
//            false,
//            null
//        )
//    )
//
//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getAllWeather().distinctUntilChanged().collect(){
//                listOfWeather ->
//                if(listOfWeather.isNotEmpty()){
//                    Log.d("Empty", "empty List")
//                }
//                else{
//                    _weatherList.value = listOfWeather
//                }
//            }
//        }
//        //getAllWeatherDetails() // get all weather details
//    }
//
//    fun getAllWeatherDetails(text: String) {
//
//        viewModelScope.launch {
//            data.value = WeatherDataOrException(
//                null,
//                true,
//                Exception("")
//            )
//            data.value.loading = true
//            data.value = repository.getWeatherData(text)
//            if (data.value.data.toString().isNotEmpty()) {
//                data.value.loading = false
//            }
//            if (data.value.data != null) {
//                // Call addWeather to add data to the database
//                addWeather(data.value.data!!.days[0])
//            }
//
//
//
//            //repository.addWeather(DayTable(data.value.data?.days?.get(0)?.datetime.toString(), data.value.data?.days?.get(0)?.tempmin!!.toDouble(), data.value.data?.days?.get(0)?.tempmax!!.toDouble(), data.value.data?.days?.get(0)?.temp!!.toDouble()))
//            //addWeather(DayTable(data.value.data?.days?.get(0)?.datetime.toString(), data.value.data?.days?.get(0)?.tempmin!!.toDouble(), data.value.data?.days?.get(0)?.tempmax!!.toDouble(), data.value.data?.days?.get(0)?.temp!!.toDouble()))
//
//
//        }
//
//         fun addWeather(day: DayTable) {
//            viewModelScope.launch {
//                repository.addWeather(
//                    DayTable(
//                        data.value.data?.days?.get(0)?.datetime.toString(),
//                        data.value.data?.days?.get(0)?.tempmin!!.toDouble(),
//                        data.value.data?.days?.get(0)?.tempmax!!.toDouble(),
//                        data.value.data?.days?.get(0)?.temp!!.toDouble()
//                    )
//                )
//
//            }
//        }
//
//    }
//
//}

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    private val _weatherList = MutableStateFlow<List<DayTable>>(emptyList())
    val weatherList = _weatherList.asStateFlow()


    val data: MutableState<WeatherDataOrException<WeatherData, Boolean, Exception>> = mutableStateOf(
        WeatherDataOrException(
            null,
            true,
            Exception("")
        )
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllWeather().distinctUntilChanged().collect{
                    listOfWeather ->
                if(listOfWeather.isNullOrEmpty()){
                    Log.d("Empty", "empty List")
                }
                else{
                    _weatherList.value = listOfWeather
                }
            }
        }
        //getAllWeatherDetails() // get all weather details
    }

    fun getAllWeatherDetails(text: String) {

        viewModelScope.launch {
            data.value = WeatherDataOrException(
                null,
                true,
                Exception("")
            )
            data.value.loading = true
            data.value = repository.getWeatherData(text)
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }

            Log.d("DATABASE", "getAllWeatherDetails BEFORE: ${data.value.data}")
            addWeather(DayTable(datetime = data.value.data?.days?.get(0)?.datetime.toString(), tempmin = data.value.data?.days?.get(0)?.tempmin!!, tempmax = data.value.data?.days?.get(0)?.tempmax!!, temp = data.value.data?.days?.get(0)?.temp!!))
            //addWeather(DayTable("2022-10-10", 10.0, 20.0, 15.0))
            Log.d("DATABASE", "getAllWeatherDetails AFTER: ${data.value.data}")


        }
    }


    fun addWeather(day: DayTable) =  viewModelScope.launch {
            repository.addWeather(day)
    }


}