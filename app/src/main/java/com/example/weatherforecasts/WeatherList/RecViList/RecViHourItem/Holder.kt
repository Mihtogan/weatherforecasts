package com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecasts.databinding.WeatherHourItemBinding


class Holder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = WeatherHourItemBinding.bind(view)

    fun bind(item: ItemHour) = with(binding) {
        teViTime.text = item.date
        //icon
        tempHour.text = item.temperatureAir.toString()//"${item.temperatureAir}C"
        precMm.text = item.prec_mm.toString()//"${item.humidity}%"
    }
}