package com.dyedfox.weatherforecast.data.network.response

import com.dyedfox.weatherforecast.data.db.entity.CurrentWeatherEntry
import com.dyedfox.weatherforecast.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)