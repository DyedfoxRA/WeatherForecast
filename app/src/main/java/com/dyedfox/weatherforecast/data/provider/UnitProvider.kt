package com.dyedfox.weatherforecast.data.provider

import com.dyedfox.weatherforecast.Internal.UnitSystem

interface UnitProvider {

 fun getUnitSystem(): UnitSystem
}