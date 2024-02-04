package com.example.weatherforecasts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecasts.GPS.GPSclient
import com.example.weatherforecasts.GPS.MyLocation
import com.example.weatherforecasts.MySharedPreferencesManager.Companion.gpsLatitude
import com.example.weatherforecasts.MySharedPreferencesManager.Companion.gpsLocPref
import com.example.weatherforecasts.MySharedPreferencesManager.Companion.gpsLongitude
import com.example.weatherforecasts.RecViList.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gpSclient: GPSclient,
    private val myOkHttpClient: MyOkHttpClient,
    private val myShPr: MySharedPreferencesManager,
) : ViewModel() {

    private val _allWeather = MutableLiveData<List<Item>>()
    val allWeather: LiveData<List<Item>>
        get() = _allWeather


    private var location = MyLocation(55.75F, 37.61F)   //Moskov

    companion object {
        const val myLog: String = "MyLog"
    }

    fun start() {
        val savLoc = MyLocation(
            myShPr.loadData(gpsLocPref, gpsLatitude),
            myShPr.loadData(gpsLocPref, gpsLongitude)
        )
        if (!((savLoc.latitude == 0.0F) && (savLoc.longitude == 0.0F)))
            location = savLoc

        updateWeatherList()

    }

    fun updateLocation() {
        gpSclient.getLocation {
            location = it

            updateWeatherList()

            myShPr.saveData(gpsLocPref, gpsLatitude, location.latitude)
            myShPr.saveData(gpsLocPref, gpsLongitude, location.longitude)
        }
    }

    private fun updateWeatherList() {
        viewModelScope.launch(Dispatchers.IO) {
            val listWeather = mutableListOf<Item>()

            myOkHttpClient.getYandexWeather(
                location.latitude, location.longitude
            )?.let {
                listWeather.add(it)
            }

            withContext(Dispatchers.Main) {
                _allWeather.value = listWeather
            }
        }
    }
}