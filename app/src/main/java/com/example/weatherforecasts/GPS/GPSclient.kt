package com.example.weatherforecasts.GPS

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.example.weatherforecasts.ViewModels.MainViewModel.Companion.myLog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GPSclient @Inject constructor(@ApplicationContext val context: Context) {
    private val fLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val ct = CancellationTokenSource()

    @SuppressLint("MissingPermission")
    fun getLocation(updateF: (MyLocation) -> Unit) {

        if (!isLocationEnable()) {
            Log.d(myLog, "GPS is Disable")
            return
        }

        fLocationClient
            .getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                it?.let {
                    val loc = MyLocation(
                        it.result.latitude.toFloat(),
                        it.result.longitude.toFloat()
                    )
                    Log.d(myLog, "loc: ${loc.latitude}, ${loc.longitude}")
                    updateF(loc)
                }
            }
    }

    private fun isLocationEnable(): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}