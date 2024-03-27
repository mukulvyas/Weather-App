package com.example.weatherapp.WeatherComponent

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    WeatherAppDetails(viewModel)

    //viewModel.getAllWeatherDetails()
}

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppDetails(viewModel: WeatherViewModel) {
    val setDisplayWeather = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Weather App") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                //elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),

            )
        }
    )  {
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Surface(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                ,color = WeatherAppColor.mOffWhite
            ) {
                Column(modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ){

                    CreatingTextBox(viewModel,setDisplayWeather)
                    if (setDisplayWeather.value) {
                        DisplayWeatherContent(viewModel)
                    }

                   // DisplayWeatherContent(viewModel)

                }
            }
        }
    }

}


@Composable
fun CreatingTextBox(viewModel: WeatherViewModel, setDisplayWeather: MutableState<Boolean>) {
    var text = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = WeatherAppColor.mOffWhite,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                },
                label = { Text("Enter the Date in YYYY-MM-DD") }
            )

            Button(
                onClick = {
                    viewModel.getAllWeatherDetails(text.value)
                    text.value = ""
                    setDisplayWeather.value = true


                },
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text = "Get Weather Details")
            }


        }
    }
}



@Composable
fun DisplayWeatherContent(viewModel: WeatherViewModel) {
    Box(modifier = Modifier,
        contentAlignment = Alignment.Center
    )    {
        Card(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(12.dp)
        ) {


            if (viewModel.data.value.loading == false) {
                MakingUI(viewModel)

            } else {
                CircularProgressIndicator()
            }
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

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        color = WeatherAppColor.mLightGray) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$Address ($TimeZone)",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 21.sp
            )
        }


        Column(
            modifier = Modifier.padding(top = 85.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "$temp",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
                )
        }
        Column(
            modifier = Modifier.padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$Date",
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif,
                //color = Color.Blue,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(8.dp)
            )
        }


        Row(
            modifier = Modifier.padding(top = 212.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.padding(10.dp)){
                OutlinedTextField(
                    value = "$minTemp",
                    onValueChange = { },
                    label = { Text("Minimum Temperature") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp),

                )

                OutlinedTextField(
                    value = "$maxTemp",
                    onValueChange = { /* No-op because it's read-only */ },
                    label = { Text("Maximum Temperature") },
                    readOnly = true, // Adjust padding as needed
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                )
            }


        }


    }


}


