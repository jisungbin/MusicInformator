package com.sungbin.musicinformator

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.sungbin.musicinformator.R


/**
 * Created by SungBin on 2020-07-30.
 */

@GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
            RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .placeholder(R.drawable.ic_baseline_album_24)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        )
    }
}