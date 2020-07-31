package com.sungbin.musicinformator.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.module.GlideApp


/**
 * Created by SungBin on 2020-07-31.
 */

data class ArtistItem(
    var name: String,
    var artistCoverUrl: String,
    var artistHeaderUrl: String,
    var artistId: Int,
    var page: Int = 1
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String?) {
            GlideApp
                .with(imageView.context)
                .load(url ?: R.drawable.ic_baseline_album_24)
                .into(imageView)
        }
    }
}