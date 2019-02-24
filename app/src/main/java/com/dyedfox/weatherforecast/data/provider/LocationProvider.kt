package com.dyedfox.weatherforecast.data.provider

import com.dyedfox.weatherforecast.data.db.entity.WeatherLocation

interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}