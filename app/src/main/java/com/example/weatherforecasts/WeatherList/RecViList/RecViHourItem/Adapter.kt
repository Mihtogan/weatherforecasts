package com.example.weatherforecasts.WeatherList.RecViList.RecViHourItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherforecasts.R


class Adapter(): ListAdapter<ItemHour, Holder>(Comparator()) {

    class Comparator : DiffUtil.ItemCallback<ItemHour>() {
        override fun areItemsTheSame(oldItem: ItemHour, newItem: ItemHour): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemHour, newItem: ItemHour): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_hour_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}