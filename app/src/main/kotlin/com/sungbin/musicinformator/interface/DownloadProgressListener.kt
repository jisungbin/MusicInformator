package com.sungbin.musicinformator.`interface`


/**
 * Created by SungBin on 2020-07-29.
 */

interface DownloadProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}