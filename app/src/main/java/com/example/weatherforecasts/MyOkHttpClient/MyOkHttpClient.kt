package com.example.weatherforecasts.MyOkHttpClient

import android.util.Log
import com.example.weatherforecasts.ViewModels.MainViewModel.Companion.myLog
import com.example.weatherforecasts.RecViList.Item
import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.ItemHour
import com.example.weatherforecasts.yandexWeather.AnswerYandexApi
import javax.inject.Inject

class MyOkHttpClient @Inject constructor(private val answerYandexApi: AnswerYandexApi) {

    /*private val client by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun createRetrofit(url: String) =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    private val urlYandex = "https://api.weather.yandex.ru"*/

    suspend fun getYandexWeather(let: Float, lon: Float): Item? {
        /*val answerYandexApi: AnswerYandexApi =
            createRetrofit(urlYandex).create(AnswerYandexApi::class.java)*/

        val response = try {
            answerYandexApi.getAnswerResponse(
                "73efab67-7609-4119-a06f-7ba79170fcb8",
                let, lon
            )
        } catch (e: Exception) {
            Log.e(myLog, "catch $e")
            return null
        }

        val responseBody = response.body()
        return if (!response.isSuccessful || responseBody == null) {
             null
        } else {
            val hourItemList =
                mutableListOf<ItemHour>()

            responseBody.forecast.parts.forEach {
                hourItemList.add(
                    ItemHour(
                        it.temp_avg, it.prec_mm
                    )
                )
            }

             Item(
                "Yandex",
              responseBody.fact.temp,
              responseBody.fact.feels_like,
              responseBody.fact.condition,
              responseBody.fact.humidity,
                hourItemList
            )
        }

    }
}