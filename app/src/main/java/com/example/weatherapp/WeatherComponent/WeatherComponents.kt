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
import androidx.compose.runtime.collectAsState
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
import androidx.core.content.ContextCompat.getSystemService
import com.example.weatherapp.WeatherModel.DayTable
import com.example.weatherapp.WeatherScreens.WeatherViewModel
import com.example.weatherapp.weatherUtlis.WeatherAppColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun WeatherDetails(viewModel: WeatherViewModel) {
    //val listDB = viewModel.weatherList.collectAsState().value
    Log.d("DisplayDB", "DisplayDatabase: ${viewModel.weatherList.collectAsState().value }")

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
    val isFromMultipleApi = remember { mutableStateOf(false) }
    val isFromApi = remember { mutableStateOf(false) }
    val isFromDB = remember { mutableStateOf(false) }
    val isResult = remember { mutableStateOf(false) }
    val dateOne = remember {
        mutableStateOf("")
    }
    val one = remember { mutableStateOf(DayTable("",0.0,0.0,0.0)) }
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


                    CreatingTextBox(viewModel,setDisplayWeather,isFromMultipleApi,isFromDB,isFromApi,isResult,one,dateOne)
                    Log.d("Actual One", "one: $one " )
                    Log.d("MAKING", "PrintWeatherDetails: ${isFromApi.value}")
                    Log.d("MAKING", "PrintWeatherDetails: ${isFromDB.value}")
                    Log.d("MAKING", "PrintWeatherDetails: ${isFromMultipleApi.value}")
//                    PrintWeatherDetails(DayTable("",0.0,0.0,0.0))

                        if (setDisplayWeather.value && isFromApi.value) {
                            DisplayWeatherContent(viewModel)
                        } else if(setDisplayWeather.value && isFromDB.value){
                            PrintWeatherDetails(one.value)
                        } else if(isFromMultipleApi.value){

                            Log.d("MAKING", "PrintWeatherDetailsNEW: ENTER")
                            MakingUISecond(viewModel,dateOne.value)
                        }
//                        } else if(!isFromMultipleApi.value && !isFromDB.value && !isFromApi.value && !isResult.value){
//                            PrintNewError()
//                        }

                    if(isResult.value){
                        Log.d("entry","checking entry")

                        PrintError()
                    }


                    // DisplayWeatherContent(viewModel)

                }
            }
        }
    }

}

@Composable
fun CreatingTextBox(
    viewModel: WeatherViewModel,
    setDisplayWeather: MutableState<Boolean>,
    isFromMultipleApi: MutableState<Boolean>,
    isFromDB: MutableState<Boolean>,
    isFromApi: MutableState<Boolean>,
    isResult: MutableState<Boolean>,
    one: MutableState<DayTable>
    ,dateOne: MutableState<String>
) {
    val listDB = viewModel.weatherList.collectAsState().value
    val text = remember { mutableStateOf("") }
    //var isResult = false
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
                    isFromMultipleApi.value = false
                    isFromDB.value = false
                    isFromApi.value = false
                    isResult.value = false
                    if (text.value.isNotEmpty() && isValidDateFormat(text.value)) {
                        val currentDate = LocalDate.now()
                        val enteredDate = LocalDate.parse(text.value, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        val matchingEntrySecond = listDB.find { it.datetime == text.value }
                        if (enteredDate.isAfter(currentDate) && matchingEntrySecond == null) {
                            val previousDates = getPreviousDatesWithSameMonthDay(enteredDate.toString())
                            Log.d("PreviousDates", "PreviousDates: $previousDates")
                            viewModel.getALlResultPrevious(previousDates, text.value)
                            val temp = viewModel.weatherStats.value?.first
                            Log.d("GettingINFO", "avg temp: $temp")
                            isFromMultipleApi.value = true
                            dateOne.value = text.value
                            text.value = ""
                        } else {
                            val matchingEntry = listDB.find{ it.datetime == text.value }
                            if (matchingEntry != null) {
                                isFromDB.value = true
                                one.value = matchingEntry
                            } else {

                                viewModel.getAllWeatherDetails(text.value)

                                Log.d("GettingINFO", "CreatingTextBoxPast: ${viewModel.data.value.data?.address}")

                                isFromApi.value = true
                            }
                            text.value = "" // Reset the text field
                            setDisplayWeather.value = true
                        }
                    } else {


                        isResult.value = true
                        text.value = ""
                    }
                }
                ,
                modifier = Modifier.padding(20.dp),
                enabled = text.value.isNotEmpty() // Disable the button if text is empty
            ) {
                Text(text = "Get Weather Details")
            }


        }
    }
}

fun getPreviousDatesWithSameMonthDay(inputDate: String): List<String> {
    //val enteredYear = inputDate.substring(0, 4).toInt()
    val enteredMonthDay = inputDate.substring(5)
    val todayYear = 2024 // Assuming the current year is 2024

    val previousDates = mutableListOf<String>()

    for (i in 1..10) {
        val previousYear = todayYear - i
        val previousDate = "$previousYear-$enteredMonthDay"
        previousDates.add(previousDate)
    }

    return previousDates
}


fun isValidDateFormat(text: String): Boolean {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return try {
        // Attempt to parse the text into a LocalDate object
        val parsedDate = LocalDate.parse(text, dateFormatter)
        // Check if the parsed date matches the original input (to handle cases like February 31st)
        val inputDateParts = text.split("-")
        parsedDate.year == inputDateParts[0].toInt() &&
                parsedDate.monthValue == inputDateParts[1].toInt() &&
                parsedDate.dayOfMonth == inputDateParts[2].toInt()
    } catch (e: DateTimeParseException) {
        // If parsing fails, return false
        false
    }
}


//
//@Composable
//fun CreatingTextBox(viewModel: WeatherViewModel, setDisplayWeather: MutableState<Boolean>, isFromDB: MutableState<Boolean> ,isFromApi: MutableState<Boolean>,one: MutableState<DayTable>) {
//    val listDB = viewModel.weatherList.collectAsState().value
//    val text = remember { mutableStateOf("") }
//    val textCopy = remember { mutableStateOf("") }
//    //val isFromDB = remember { mutableStateOf(false) }
//    val matchingEntry = listDB.find { it.datetime == text.value }
//    //val matchingEntryOne = listDB.find { it.datetime == text.value }
//    Log.d("Main MatchingEntry", "MatchingEntry: $matchingEntry" )
//    Surface(
//        modifier = Modifier.fillMaxWidth(),
//        color = WeatherAppColor.mOffWhite,
//    ) {
//        Column(
//            modifier = Modifier.padding(12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            OutlinedTextField(
//                value = text.value,
//                onValueChange = {
//                    text.value = it
//                    textCopy.value = it
//                },
//                label = { Text("Enter the Date in YYYY-MM-DD") }
//            )
//
//            Button(
//                onClick = {
//                    if (text.value.isNotEmpty()) {
//                        //var matchingEntry = listDB.find { it.datetime == text.value }
//                        Log.d("MatchingEntry Before", "MatchingEntry: $matchingEntry" )
//                        if (matchingEntry != null) {
//                            // If a matching entry is found in the database, print its details
//                            isFromDB.value = true
//                            Log.d("MatchingEntry After", "MatchingEntry: $matchingEntry" )
//
//
//                        } else {
//                            viewModel.getAllWeatherDetails(text.value)
//                            isFromApi.value = true
//                        }
//                        text.value = "" // Reset the text field
//                        setDisplayWeather.value = true
//                    }
//                },
//                modifier = Modifier.padding(20.dp),
//                enabled = text.value.isNotEmpty() // Disable the button if text.value is empty
//            ) {
//                Text(text = "Get Weather Details")
//            }
//        }
//    }
//
//    if (isFromDB.value) {
//        Log.d("before", "one: $listDB" )
//        one.value = listDB.find { it.datetime == textCopy.value }!!
//        Log.d("after", "one: $listDB $one " )
////        if (one != null) {
////            PrintWeatherDetails(one)
////        }
//        textCopy.value = ""
//    }
//
//
//}



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
                Log.d("OFFLINE", "PrintWeatherDetails: Entry")
                MakingUI(viewModel)

            } else {
                Log.d("Online", "PrintWeatherDetailsCircular: Entry ${viewModel.data.value.loading}")

                CircularProgressIndicator()
                Log.d("Online", "PrintWeatherDetailsCircular: Exit ${viewModel.data.value.loading}")

            }
        }
    }

}

@Composable
fun MakingUISecond(viewModel: WeatherViewModel,dateOne: String){
    val minTemp = String.format("%.2f", viewModel.weatherStats.value?.second)
    val maxTemp = String.format("%.2f", viewModel.weatherStats.value?.third)
    val temp = String.format("%.2f", viewModel.weatherStats.value?.first)
    val address = "New Delhi"
    val timeZone = "Asia/Kolkata"


    Box(modifier = Modifier,
        contentAlignment = Alignment.Center
    )    {
        Card(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(12.dp)
        ) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = WeatherAppColor.mLightGray) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$address ($timeZone)",
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 21.sp
                    )
                }


                Column(
                    modifier = Modifier.padding(top = 85.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = temp,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                }
                Column(
                    modifier = Modifier.padding(top = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dateOne,
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
                            value = minTemp,
                            onValueChange = { },
                            label = { Text("Minimum Temperature") },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp, bottom = 5.dp),

                            )

                        OutlinedTextField(
                            value = maxTemp,
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
    }
    Log.d("PRINTEATHER", "PrintWeatherDetails: EXIT")
}

@Composable
fun MakingUI(viewModel: WeatherViewModel){
    Log.d("MAKINGUI", "PrintWeatherDetails: ENTER")
    val minTemp = viewModel.data.value.data?.days?.get(0)?.tempmin
    val maxTemp = viewModel.data.value.data?.days?.get(0)?.tempmax
    val temp = viewModel.data.value.data?.days?.get(0)?.temp
    val date = viewModel.data.value.data?.days?.get(0)?.datetime
    var address = viewModel.data.value.data?.address
    var timeZone = viewModel.data.value.data?.timezone
    //val QueryCost = viewModel.data.value.data?.queryCost
    //val Latitude = viewModel.data.value.data?.latitude
    //val Longitude = viewModel.data.value.data?.longitude
    //val ResolvedAddress = viewModel.data.value.data?.resolvedAddress
    //val TZOffset = viewModel.data.value.data?.tzoffset
    if (address == null){
        address = "New Delhi"
    }
    if (timeZone == null){
        timeZone = "Asia/Kolkata"
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        color = WeatherAppColor.mLightGray) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$address ($timeZone)",
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
                text = "$date",
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
    Log.d("MAKINGUI", "PrintWeatherDetails: EXIT")

}


@Composable
fun PrintWeatherDetails(entry: DayTable){
    Log.d("PRINTEATHER", "PrintWeatherDetails: Entry")
    val minTemp = String.format("%.2f",entry.tempmin)
    val maxTemp = String.format("%.2f",entry.tempmax)
    val temp = String.format("%.2f",entry.temp)
    val date = entry.datetime
    val address = "New Delhi"
    val timeZone = "Asia/Kolkata"


    Box(modifier = Modifier,
        contentAlignment = Alignment.Center
    )    {
        Card(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(12.dp)
        ) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = WeatherAppColor.mLightGray) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$address ($timeZone)",
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
                        text = date,
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
    }
    Log.d("PRINTEATHER", "PrintWeatherDetails: EXIT")



}


@Composable
fun PrintError(){
    Log.d("PRINTEATHER", "PrintWeatherDetails: Entry")



    Box(modifier = Modifier,
        contentAlignment = Alignment.Center
    )    {
        Card(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(12.dp)
        ) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = WeatherAppColor.mLightGray) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("PRINTEATHER", "PrintWeatherDetails: EXIT")
                    Text(
                        text ="Please Enter a valid Date in YYYY-MM-DD format",
                            color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 21.sp
                    )
                }




            }

        }
    }
    Log.d("PRINTEATHER", "PrintWeatherDetails: EXIT")



}


@Composable
fun PrintNewError(){
    Log.d("PRINTEATHER", "PrintWeatherDetails: Entry")



    Box(modifier = Modifier,
        contentAlignment = Alignment.Center
    )    {
        Card(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(12.dp)
        ) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = WeatherAppColor.mLightGray) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("PRINTEATHER", "PrintWeatherDetails: EXIT")
                    Text(
                        text ="We're unable to find your required data in the database or you are not connected to internet. Please try again or connect to internet.",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 21.sp
                    )
                }




            }

        }
    }
    Log.d("PRINTEATHER", "PrintWeatherDetails: EXIT")



}