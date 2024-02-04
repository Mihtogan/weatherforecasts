package com.example.weatherforecasts.yandexWeather

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AnswerYandexApi {

    @GET("v2/informers")
    fun getAnswer(
        @Header("X-Yandex-API-Key") key: String,
        @Query("lat") let: Float,
        @Query("lon") lon: Float,
        @Query("lang") lang: String = "ru_RU"
    ): Call<AnswerYandex>

    @GET("v2/informers")
    suspend fun getAnswerResponse(
        @Header("X-Yandex-API-Key") key: String,
        @Query("lat") let: Float,
        @Query("lon") lon: Float,
        @Query("lang") lang: String = "ru_RU"
    ): Response<AnswerYandex>
}