package com.sungbin.musicinformator.utils

import android.view.View
import android.widget.TextView
import com.google.gson.JsonObject
import com.sungbin.musicinformator.MusicInformator
import com.sungbin.sungbintool.ToastUtils


/**
 * Created by SungBin on 2020-07-28.
 */

fun toast(message: Any?, duration: Int = ToastUtils.SHORT, type: Int = ToastUtils.SUCCESS) {
    ToastUtils.show(MusicInformator.context, message.toString(), duration, type)
}

fun View.hide(isGone: Boolean = false){
    this.visibility = if (isGone) View.GONE else View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

operator fun TextView.plusAssign(text: String) {
    this.text = text
}

fun TextView.clear() {
    this.text = ""
}

fun JsonObject.getString(keyword: String) = this[keyword].toString().replace("\"", "")