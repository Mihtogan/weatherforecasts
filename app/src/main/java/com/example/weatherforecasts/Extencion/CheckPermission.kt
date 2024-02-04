package com.example.weatherforecasts.Extencion

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(permName: String): Boolean {
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity, permName
    ) == PackageManager.PERMISSION_GRANTED
}