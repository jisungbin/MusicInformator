package com.sungbin.musicinformator.utils

import android.util.Log
import org.json.JSONObject

object LogUtils {

    fun log(vararg message: Any?) {
        for (element in message) {
            Log.d("LogUtils", element.toString())
        }
    }

    fun json(json: Any) {
        Log.d("JSON", JSONObject(json.toString()).toString(4))
    }
}