package com.example.weatherforecasts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.RecViList.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _allWeather = MutableLiveData<List<Item>>()
    val allWeather: LiveData<List<Item>>
        get() = _allWeather

    private val myOkHttpClient = MyOkHttpClient()

    fun updateWeatherList() {
        /* myOkHttpClient.getYandexWeather {
             _allWeather.value = listOf(it)
         }*/

        viewModelScope.launch(Dispatchers.IO) {

            myOkHttpClient.getYandexWeather()?.let {
                withContext(Dispatchers.Main) {
                    _allWeather.value = listOf(it)
                }
            }
        }
    }
}