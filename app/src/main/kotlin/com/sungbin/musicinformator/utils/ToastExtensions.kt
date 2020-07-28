package com.sungbin.musicinformator.utils

import com.sungbin.musicinformator.MusicInformator
import com.sungbin.sungbintool.ToastUtils


/**
 * Created by SungBin on 2020-07-28.
 */

fun toast(message: String, duration: Int = ToastUtils.SHORT, type: Int = ToastUtils.SUCCESS) {
    ToastUtils.show(MusicInformator.context, message, duration, type)
}