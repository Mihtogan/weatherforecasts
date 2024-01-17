package com.example.weatherforecasts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item
import com.example.weatherforecasts.databinding.FragmentMainBinding
import com.example.weatherforecasts.yandexWeather.AnswerYandex
import com.example.weatherforecasts.yandexWeather.AnswerYandexApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //
        val weatherList = mutableListOf<com.example.weatherforecasts.RecViList.Item>()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(ChuckerInterceptor(requireContext()))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val answerYandexApiApi: AnswerYandexApi = retrofit.create(AnswerYandexApi::class.java)

        val callback = object : Callback<AnswerYandex> {
            override fun onResponse(cal: Call<AnswerYandex>, response: Response<AnswerYandex>) {
                // в случае получения ответа

                if (response.body() == null) return

                val hourItemList = mutableListOf<Item>()

                response.body()!!.forecast.parts.forEach {
                    hourItemList.add(Item(it.temp_avg, it.prec_mm))
                }

                weatherList.add(
                    com.example.weatherforecasts.RecViList.Item(
                        "Yandex",
                        response.body()!!.fact.temp,
                        response.body()!!.fact.feels_like,
                        response.body()!!.fact.condition,
                        response.body()!!.fact.humidity,
                        hourItemList
                    )
                )

                mainViewModel.allWeather.value = weatherList
            }

            override fun onFailure(p0: Call<AnswerYandex>, p1: Throwable) {
                // в случае получения ошибки
            }
        }

        answerYandexApiApi.getAnswer(
            "73efab67-7609-4119-a06f-7ba79170fcb8",
            54.85, 83.05
        ).enqueue(callback)
        //

        findNavController().navigate(R.id.action_mainFragment_to_weatherListFragment)
    }

}