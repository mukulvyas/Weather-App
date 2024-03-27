package com.example.weatherapp.WeatherScreens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.WeatherComponent.WeatherDetails


@Composable
fun WeatherHomeApp(viewModel: WeatherViewModel = hiltViewModel()) {
    WeatherDetails(viewModel)

}