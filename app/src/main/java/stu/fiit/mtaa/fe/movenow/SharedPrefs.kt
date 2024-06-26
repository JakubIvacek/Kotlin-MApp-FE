package stu.fiit.mtaa.fe.movenow

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        }
    }

    fun getInstance(): SharedPreferences {
        if (sharedPreferences != null) {
            return sharedPreferences!!
        } else {
            throw IllegalStateException("Prefs not initialized. Call Prefs.init(context) before using it.")
        }
    }
}