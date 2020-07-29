package com.sungbin.musicinformator.network

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*


/**
 * Created by SungBin on 2020-07-29.
 */
class ProgressResponseBody(
    val responseBody: ResponseBody,
    val onAttachmentDownloadUpdate: (Int) -> Unit
) : ResponseBody() {

    private var bufferedSource = source(responseBody.source()).buffer()

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource {
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)

                totalBytesRead += if (bytesRead != -1L) bytesRead else 0

                val percent =
                    if (bytesRead == -1L) 100f else totalBytesRead.toFloat() / responseBody.contentLength()
                        .toFloat() * 100

                onAttachmentDownloadUpdate(percent.toInt())

                return bytesRead
            }
        }
    }
}