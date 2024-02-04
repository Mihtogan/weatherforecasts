package com.example.weatherforecasts

import android.util.Log
import com.example.weatherforecasts.MainViewModel.Companion.myLog
import com.example.weatherforecasts.RecViList.Item
import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.ItemHour
import com.example.weatherforecasts.yandexWeather.AnswerYandexApi
import javax.inject.Inject

class MyOkHttpClient @Inject constructor(private val answerYandexApi: AnswerYandexApi) {

    suspend fun getYandexWeather(let: Float, lon: Float): Item? {

        val response = try {
            answerYandexApi.getAnswerResponse(
                "73efab67-7609-4119-a06f-7ba79170fcb8",
                let, lon
            )
        } catch (e: Exception) {
            Log.e(myLog, "catch $e")
            return null
        }

        if (!response.isSuccessful || response.body() == null) return null

        val hourItemList =
            mutableListOf<ItemHour>()

        response.body()!!.forecast.parts.forEach {
            hourItemList.add(
                ItemHour(
                    it.temp_avg, it.prec_mm
                )
            )
        }

        return Item(
            "Yandex",
            response.body()!!.fact.temp,
            response.body()!!.fact.feels_like,
            response.body()!!.fact.condition,
            response.body()!!.fact.humidity,
            hourItemList
        )

    }
}