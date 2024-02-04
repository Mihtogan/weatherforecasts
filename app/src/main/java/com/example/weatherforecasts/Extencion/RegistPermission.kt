package com.example.weatherforecasts.Extencion

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment

fun Fragment.registPermission(permName: String) {
    if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) return

    val pLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            //Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }

    pLauncher.launch(permName)
}