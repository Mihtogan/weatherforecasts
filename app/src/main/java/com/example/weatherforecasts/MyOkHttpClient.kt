package com.example.weatherforecasts

import android.util.Log
import com.example.weatherforecasts.RecViList.Item
import com.example.weatherforecasts.yandexWeather.AnswerYandex
import com.example.weatherforecasts.yandexWeather.AnswerYandexApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyOkHttpClient {

    private val client by lazy {
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


    private val urlYandex = "https://api.weather.yandex.ru"

    suspend fun getYandexWeather(): Item? {
        val answerYandexApi: AnswerYandexApi =
            createRetrofit(urlYandex).create(AnswerYandexApi::class.java)

        val response = try {
            answerYandexApi.getAnswerResponse(
                "73efab67-7609-4119-a06f-7ba79170fcb8",
                54.85, 83.05
            )
        } catch (e: Exception) {
            Log.e("MyLog", "catch $e")
            return null
        }

        if (!response.isSuccessful || response.body() == null) return null

        val hourItemList =
            mutableListOf<com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item>()

        response.body()!!.forecast.parts.forEach {
            hourItemList.add(
                com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item(
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

    fun getYandexWeather(callback: (Item) -> Unit) {
        val answerYandexApi: AnswerYandexApi =
            createRetrofit(urlYandex).create(AnswerYandexApi::class.java)

        answerYandexApi.getAnswer(
            "73efab67-7609-4119-a06f-7ba79170fcb8",
            54.85, 83.05
        ).enqueue(
            object : Callback<AnswerYandex> {
                override fun onResponse(cal: Call<AnswerYandex>, response: Response<AnswerYandex>) {
                    // в случае получения ответа
                    Log.e("MyLog", "onResponse: ")

                    if (response.body() == null) return

                    val hourItemList =
                        mutableListOf<com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item>()

                    response.body()!!.forecast.parts.forEach {
                        hourItemList.add(
                            com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item(
                                it.temp_avg, it.prec_mm
                            )
                        )
                    }

                    callback(
                        Item(
                            "Yandex",
                            response.body()!!.fact.temp,
                            response.body()!!.fact.feels_like,
                            response.body()!!.fact.condition,
                            response.body()!!.fact.humidity,
                            hourItemList
                        )
                    )
                }

                override fun onFailure(p0: Call<AnswerYandex>, p1: Throwable) {
                    // в случае получения ошибки
                    Log.e("MyLog", "onFailure")
                    //callback(null)
                }
            }
        )

        Log.e("MyLog", "finish getYanWet ")
    }
}