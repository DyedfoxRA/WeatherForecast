package com.dyedfox.weatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.dyedfox.weatherforecast.data.db.CurrentWeatherDao
import com.dyedfox.weatherforecast.data.db.WeatherLocationDao
import com.dyedfox.weatherforecast.data.db.entity.WeatherLocation
import com.dyedfox.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.dyedfox.weatherforecast.data.network.WeatherNetworkDataSource
import com.dyedfox.weatherforecast.data.network.response.CurrentWeatherResponse
import com.dyedfox.weatherforecast.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            presistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
       return withContext(Dispatchers.IO){
           initWeatheData()
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
           else currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun presistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse)
    {
        GlobalScope.launch(Dispatchers.IO){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatheData()
    {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation))
        {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

    }

    private suspend fun fetchCurrentWeather()
    {
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(),
            Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean
    {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)

        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}