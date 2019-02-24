package com.dyedfox.weatherforecast.data.network.response

import com.dyedfox.weatherforecast.data.db.entity.CurrentWeatherEntry
import com.dyedfox.weatherforecast.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry

)