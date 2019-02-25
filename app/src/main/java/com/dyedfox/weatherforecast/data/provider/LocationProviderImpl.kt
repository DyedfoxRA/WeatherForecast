package com.dyedfox.weatherforecast.data.provider

import android.content.Context
import com.dyedfox.weatherforecast.data.db.entity.WeatherLocation

class LocationProviderImpl(context: Context) : PreferenceProvider(context), LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Los Angeles"
    }
}