package com.example.weatherforecasts

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MySharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val gpsLatitude: String = "gpsCoordinatesLatitude"
        const val gpsLongitude: String = "gpsCoordinatesLongitude"
        const val gpsLocPref: String = "gpsLocationPreferences"
    }

    fun saveData(
        table: String,
        key: String,
        res: Float,
        mode: Int = Context.MODE_PRIVATE
    ) {
        val editor = context
            .getSharedPreferences(table, mode).edit()

        editor.putFloat(key, res)
        editor.apply()
    }

    fun loadData(
        table: String,
        key: String,
        mode: Int = Context.MODE_PRIVATE
    ): Float {
        return context.getSharedPreferences(table, mode)
            .getFloat(key, 0F)
    }
}