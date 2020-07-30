package com.sungbin.musicinformator.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sungbin.musicinformator.GlideApp
import com.sungbin.musicinformator.R


/**
 * Created by SungBin on 2020-06-21.
 */

data class SongItem(
    var name: String,
    var artist: String,
    var albumImageUrl: String?,
    var trackId: Long = 0L,
    var albumId: Long = 0L,
    var songId: Int = 0
) {
    companion object {

        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun loadImage(imageView: ImageView, url: String?) {
            GlideApp
                .with(imageView.context)
                .load(url ?: R.drawable.ic_baseline_album_24)
                .into(imageView)
        }
    }
}