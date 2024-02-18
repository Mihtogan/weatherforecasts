package com.example.weatherforecasts.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.weatherforecasts.CustomView.CoordinatesXY
import com.example.weatherforecasts.CustomView.DataForChart
import com.example.weatherforecasts.ViewModels.MainViewModel
import com.example.weatherforecasts.databinding.FragmentWeatherChartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherChartFragment : Fragment() {
    private lateinit var binding: FragmentWeatherChartBinding
    private val mainViewModule: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherChartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModule.allWeather.observe(activity as LifecycleOwner) { it ->

            val pointTemperature = mutableListOf<DataForChart>()

            it.forEach { items ->

                val hourTemperature = mutableListOf<CoordinatesXY>()

                items.hour.forEachIndexed { index, hourItems ->
                    hourTemperature.add(
                        CoordinatesXY(
                            index.toFloat(), hourItems.temperatureAir
                        )
                    )
                }

                pointTemperature.add(DataForChart(items.serviceName, hourTemperature))
            }

            binding.temperatureChart.setData(pointTemperature)
        }

/*
        val exemptData = listOf(
            DataForChart(
                "",
                listOf(
                    CoordinatesXY(0f, 5f),
                    CoordinatesXY(1f, 8f),
                    CoordinatesXY(2f, 9f),
                    CoordinatesXY(3f, 9f),
                    CoordinatesXY(4f, 10f),
                    CoordinatesXY(5f, 8f),
                    CoordinatesXY(6f, 6f),
                )
            ),
            DataForChart(
                "",
                listOf(
                    CoordinatesXY(0f, 4f),
                    CoordinatesXY(1f, 3f),
                    CoordinatesXY(2f, 6f),
                    CoordinatesXY(3f, 7f),
                    CoordinatesXY(4f, 7f),
                    CoordinatesXY(5f, 6f),
                    CoordinatesXY(6f, 5f),
                    CoordinatesXY(7f, 4f),
                )
            )
        )

        binding.temperatureChart.setData(exemptData)*/
    }

}