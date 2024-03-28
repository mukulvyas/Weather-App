package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.WeatherScreens.WeatherHomeApp
import com.example.weatherapp.WeatherScreens.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val weatherViewModel = viewModels<WeatherViewModel>()

                    WeatherHomeApp()
                    //Log.d("DisplayDatabase", "DisplayDatabase: ${weatherViewModel.value.weatherList.collectAsState().value }")

                }
            }
        }
    }
}

//@Composable
//fun DisplayDatabase(weatherViewModel: List<WeatherViewModel>) {
//
//    val weathersList = weatherViewModel.value.weatherList.collectAsState().value
//    Log.d("DisplayDatabase", "DisplayDatabase: ${weatherViewModel.weatherList.collectAsState().value }")
//}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        WeatherHomeApp()
    }
}