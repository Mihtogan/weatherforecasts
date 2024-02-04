package com.example.weatherforecasts

import com.example.weatherforecasts.yandexWeather.AnswerYandexApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyOkHttpModule {

    @Provides
    @Singleton
    fun providesMyOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    @Provides
    @Singleton
    fun providesYandexApi(client: OkHttpClient): AnswerYandexApi {
        return Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AnswerYandexApi::class.java)
    }

}