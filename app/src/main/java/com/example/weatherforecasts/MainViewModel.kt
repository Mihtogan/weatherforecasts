package com.example.weatherforecasts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecasts.RecViList.Item

class MainViewModel : ViewModel() {
    val allWeather = MutableLiveData<List<Item>>()
}