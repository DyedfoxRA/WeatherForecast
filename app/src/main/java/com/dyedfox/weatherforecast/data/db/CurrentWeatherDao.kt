package com.dyedfox.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dyedfox.weatherforecast.data.db.entity.CURRENT_WEATHER_ID
import com.dyedfox.weatherforecast.data.db.entity.CurrentWeatherEntry
import com.dyedfox.weatherforecast.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.dyedfox.weatherforecast.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.dyedfox.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from currentweather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>

    @Query("select * from currentweather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>
}