package com.sungbin.musicinformator.utils

import android.util.Log
import com.google.gson.GsonBuilder
import org.json.JSONObject

object LogUtils {

    fun log(vararg message: Any?) {
        for (element in message) {
            Log.d("LogUtils", element.toString())
        }
    }

    fun json(string: String) {
        Log.d("JSON", JSONObject(string).toString(4))
    }
}