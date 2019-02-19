package com.dyedfox.weatherforecast.data.network

import android.location.Location
import androidx.lifecycle.LiveData
import com.dyedfox.weatherforecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}