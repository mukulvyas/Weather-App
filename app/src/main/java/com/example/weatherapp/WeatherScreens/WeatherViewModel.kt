package com.example.weatherapp.WeatherScreens

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
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
import kotlin.math.log
import kotlin.time.Duration.Companion.days

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


    fun getAllPreviousWeatherDetails(previousDates: List<String>, date: String): Triple<Double, Double, Double>{

        val deferredWeatherDataList = mutableListOf<WeatherData>().toMutableList()
        val mainList = mutableListOf<WeatherData>()
        var totalTemp = 0.0
        var totalTempMin = 0.0
        var totalTempMax = 0.0
        var count = 0



        viewModelScope.launch {
//            try {
                data.value = WeatherDataOrException(null, true, Exception(""))
                data.value.loading = true
                //val deferredWeatherDataList = mutableListOf<Deferred<WeatherDataOrException>>()
//                val deferredWeatherData = previousDates.map { date ->
//                    async {
//                        Log.d("EachWeatherAPI", "getAllPreviousWeatherDetails: $date")
//                        val getAPI = repository.getWeatherData(date)
//                        deferredWeatherDataList.add(getAPI.data!!)
//                        Log.d("EachWeatherAPI", "getAllPreviousWeatherDetails: $getAPI.data!!")
//                    }
//                }
//                Log.d("EachWeatherAPIMAIN", "getAllPreviousWeatherDetails: $deferredWeatherDataList")
//
//                // Await for all the API calls to complete
//                val weatherDataList = deferredWeatherData.awaitAll()


//                Log.d("Multiple API", "getAllPreviousWeatherDetails: $weatherDataList")


                for (dateSecond in previousDates) {
                    val getAPI = repository.getWeatherData(dateSecond)
                    mainList.add(getAPI.data!!)

                    Log.d("EachWeatherAPI", "getAllPreviousWeatherDetails: $getAPI.data!!")
                }

                for(weatherData in mainList) {
                    weatherData.days.forEach {day ->

                        totalTemp += day.temp
                        totalTempMin += day.tempmin
                        totalTempMax += day.tempmax
                        count++

                    }

                    Log.d("EachYearAPIRequest", "EachYearAPIRequest:  $weatherData")
                }
                totalTemp /= count
                totalTempMin /= count
                totalTempMax /= count
                Log.d("AverageResult", "getAllPreviousWeatherDetails:  total: $totalTemp min: $totalTempMin  max: $totalTempMax")
                addWeather(DayTable(datetime = date.toString(), tempmin = totalTempMin, tempmax = totalTempMax, temp = totalTemp))




//            } catch (e: Exception) {
//                // Handle exceptions if any
//                data.value = WeatherDataOrException(null, false, e)
//            }
        }
        return Triple(totalTemp, totalTempMin, totalTempMax)

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