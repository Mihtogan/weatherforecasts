package com.example.weatherforecasts.RecViList

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem.Adapter
import com.example.weatherforecasts.databinding.WeatherListItemBinding


class Holder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = WeatherListItemBinding.bind(view)

    fun bind(item: Item) = with(binding) {
        serviceName.text = item.serviceName
        //icon
        temperature.text = item.temperatureAir.toString() //"${item.temperatureAir}C"
        temperature2.text = item.temperatureComfort.toString() //"по ощющению: ${item.temperatureAir}C"
        humidity.text = item.humidity.toString() //"${item.humidity}%"
        descriptionWeather.text = item.description

        val rvViHourAdapter = Adapter()
        binding.weatherHour.adapter = rvViHourAdapter

        rvViHourAdapter.submitList(item.hour)
    }
}