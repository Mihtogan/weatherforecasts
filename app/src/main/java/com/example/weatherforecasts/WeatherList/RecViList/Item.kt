package com.example.weatherforecasts.RecViList

import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item

data class Item(
    val serviceName: String,
    val temperatureAir: Int,
    val temperatureComfort: Int,
    val description: String,
    val humidity: Int,
    val hour: List<Item>,
    val icon: String? = null,
)


