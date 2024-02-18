package com.example.weatherforecasts.MyGeocoder

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherforecasts.ViewModels.MainViewModel.Companion.myLog
import java.io.IOException
import javax.inject.Inject

class MyGeocoder @Inject constructor(
    private val geocoder: Geocoder
) {

    suspend fun getCoordinatesFromAddress(
        address: String,
        listener: (Address) -> Unit
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getNewSDK(address, listener)
            } else {
                getOldSDK(address, listener)
            }

        } catch (e: IOException) {
            Log.d(myLog, e.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private suspend fun getNewSDK(
        address: String,
        listener: (Address) -> Unit
    ) {
        geocoder.getFromLocationName(address, 1,
            object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    listener(addresses[0])
                }

                override fun onError(errorMessage: String?) {
                    Log.d(myLog, errorMessage.orEmpty())
                }
            })
    }

    private suspend fun getOldSDK(
        address: String,
        listener: (Address) -> Unit
    ) {
        val addresses = geocoder.getFromLocationName(address, 1)

        addresses?.let {
            if (addresses.isNotEmpty()) {
                listener(addresses[0])
            }
        }
    }
}