package com.example.weatherforecasts.WeatherList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherforecasts.R
import com.example.weatherforecasts.RecViList.Adapter
import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Item
import com.example.weatherforecasts.databinding.FragmentWeatherListBinding

class WeatherListFragment : Fragment() {

    private lateinit var binding: FragmentWeatherListBinding

    private val reViListAdapter = Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ReViWeatList.adapter = reViListAdapter

        val itemHour = listOf(
            Item(date = "00:00", temperatureAir = 11, prec_mm = 4),
            Item(date = "01:00", temperatureAir = 12, prec_mm = 1),
            Item(date = "02:00", temperatureAir = 13, prec_mm = 2),
            Item(date = "03:00", temperatureAir =  14, prec_mm =  3),
            Item(date = "04:00", temperatureAir = 15, prec_mm =  4),
            Item(date = "05:00", temperatureAir = 16, prec_mm =  9),
            Item(date = "06:00", temperatureAir =  17, prec_mm =  4),
            Item(date = "07:00", temperatureAir = 18, prec_mm = 2)
        )

        reViListAdapter.submitList(
            listOf(
                com.example.weatherforecasts.RecViList.Item(
                    "Test2",
                    20,
                    15,
                    "облочно,очень",
                    77,
                    itemHour
                ),
                com.example.weatherforecasts.RecViList.Item(
                    "Test",
                    21,
                    19,
                    "облочно",
                    90,
                    itemHour
                ),
            )
        )
    }

}