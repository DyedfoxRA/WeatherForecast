package com.dyedfox.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.dyedfox.weatherforecast.Internal.UnitSystem
import com.dyedfox.weatherforecast.Internal.lazyDeffender
import com.dyedfox.weatherforecast.data.provider.UnitProvider
import com.dyedfox.weatherforecast.data.repository.ForecastRepository

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeffender {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
