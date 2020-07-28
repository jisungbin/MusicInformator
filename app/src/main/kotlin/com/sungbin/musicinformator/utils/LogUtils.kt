package com.sungbin.musicinformator.utils

import android.util.Log

object LogUtils {

    fun log(vararg message: Any?){
        for (element in message){
            Log.d("LogUtils", element.toString())
        }
    }

}