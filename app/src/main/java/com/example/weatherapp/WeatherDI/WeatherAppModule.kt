package com.example.weatherapp.WeatherDI

import androidx.room.Room
import com.example.weatherapp.WeatherData.WeatherDatabase
import com.example.weatherapp.WeatherData.WeatherDatabaseDao
import com.example.weatherapp.WeatherNetwork.WeatherAPI
import com.example.weatherapp.WeatherRepository.Repository
import com.example.weatherapp.weatherUtlis.WeatherConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherAppModule {
    @Singleton
    @Provides
    fun provideRepository(WeatherAPI: WeatherAPI): Repository = Repository(WeatherAPI)
    @Singleton
    @Provides
    fun provideWeatherAPI(): WeatherAPI{
        return Retrofit.Builder()
            .baseUrl(WeatherConstants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDatabaseDao =
        weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: android.content.Context): WeatherDatabase
    = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database")
        .fallbackToDestructiveMigration()
        .build()



}