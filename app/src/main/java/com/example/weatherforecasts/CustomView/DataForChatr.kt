package com.example.weatherforecasts.CustomView

data class DataForChart(
    val name: String,
    val coordinatesXY: List<CoordinatesXY>
)

data class CoordinatesXY(
    val x: Float,
    val y: Float
)
