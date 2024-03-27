package com.example.weatherapp.WeatherComponent

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.WeatherScreens.WeatherViewModel
import com.example.weatherapp.weatherUtlis.WeatherAppColor

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
    }else{
        Log.d("WeatherDetails", "MinTemp: $minTemp")
        Log.d("WeatherDetails", "MaxTemp: $maxTemp")
        Log.d("WeatherDetails", "Temp: $temp")
    }
    WeatherDetails()
    //viewModel.getAllWeatherDetails()
}

@Preview
@Composable
fun WeatherDetails() {
   Surface(modifier = Modifier
       .fillMaxHeight()
       .fillMaxWidth()
       ,color = WeatherAppColor.mOffWhite
       ) {
        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
            ){
            CreatingTextBox()
        }
   }
}


@Preview
@Composable
fun CreatingTextBox(){
    var text by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxWidth()
        ,color = WeatherAppColor.mOffWhite,
        ){
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,){


            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter the Date in YYYY-MM-DD") }
            )

            Button(onClick = { /*TODO*/ },
                modifier = Modifier.padding(20.dp)) {
                Text(text = "Get Weather Details")
            }

        }
    }
}