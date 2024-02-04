package com.example.weatherforecasts.RecViList

import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.ItemHour

data class Item(
    val serviceName: String,
    val temperatureAir: Float,
    val temperatureComfort: Float,
    val description: String,
    val humidity: Float,
    val hour: List<ItemHour>,
    val icon: String? = null,
)


