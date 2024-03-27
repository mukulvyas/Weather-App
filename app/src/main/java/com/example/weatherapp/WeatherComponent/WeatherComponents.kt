package com.example.weatherapp.WeatherComponent

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.weatherapp.WeatherScreens.WeatherViewModel

@Composable
fun WeatherDetails(viewModel: WeatherViewModel) {
    val minTemp = viewModel.data.value.data?.days?.get(0)?.tempmin
    val maxTemp = viewModel.data.value.data?.days?.get(0)?.tempmax
    val temp = viewModel.data.value.data?.days?.get(0)?.temp
//     val Date = viewModel.data.value.data?.days?.get(0)?.datetime
//    val Address = viewModel.data.value.data?.address
//    val TimeZone = viewModel.data.value.data?.timezone
//    val QueryCost = viewModel.data.value.data?.queryCost
//    val Latitude = viewModel.data.value.data?.latitude
//    val Longitude = viewModel.data.value.data?.longitude
//    val ResolvedAddress = viewModel.data.value.data?.resolvedAddress
//    val TZOffset = viewModel.data.value.data?.tzoffset
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d("WeatherDetails", "Loading")
    }else{
        Log.d("WeatherDetails", "MinTemp: $minTemp")
        Log.d("WeatherDetails", "MaxTemp: $maxTemp")
        Log.d("WeatherDetails", "Temp: $temp")
    }

    //viewModel.getAllWeatherDetails()
}
