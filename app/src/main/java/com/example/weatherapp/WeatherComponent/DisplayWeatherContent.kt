package com.example.weatherapp.WeatherComponent

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.WeatherScreens.WeatherViewModel


@Composable
fun DisplayWeatherContent(viewModel: WeatherViewModel) {
    Card(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(12.dp)) {


        if (viewModel.data.value.loading == false) {
            MakingUI(viewModel)

        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun MakingUI(viewModel: WeatherViewModel){
    val minTemp = viewModel.data.value.data?.days?.get(0)?.tempmin
    val maxTemp = viewModel.data.value.data?.days?.get(0)?.tempmax
    val temp = viewModel.data.value.data?.days?.get(0)?.temp
    val Date = viewModel.data.value.data?.days?.get(0)?.datetime
    val Address = viewModel.data.value.data?.address
    val TimeZone = viewModel.data.value.data?.timezone
    val QueryCost = viewModel.data.value.data?.queryCost
    val Latitude = viewModel.data.value.data?.latitude
    val Longitude = viewModel.data.value.data?.longitude
    val ResolvedAddress = viewModel.data.value.data?.resolvedAddress
    val TZOffset = viewModel.data.value.data?.tzoffset


    Text(text = "minTemp: $minTemp")
    Text(text = "maxTemp: $maxTemp")
    Text(text = "temp: $temp")
    Text(text = "Date: $Date")
    Text(text = "Address: $Address")
    Text(text = "TimeZone: $TimeZone")
//            Text(text = "QueryCost: $QueryCost")
//            Text(text = "Latitude: $Latitude")
//            Text(text = "Longitude: $Longitude")
//            Text(text = "ResolvedAddress: $ResolvedAddress")
//            Text(text = "TZOffset: $TZOffset")

}