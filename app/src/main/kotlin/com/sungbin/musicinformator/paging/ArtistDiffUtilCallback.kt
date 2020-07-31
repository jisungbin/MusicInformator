package com.sungbin.musicinformator.paging

import androidx.recyclerview.widget.DiffUtil
import com.sungbin.musicinformator.model.ArtistItem


/**
 * Created by SungBin on 2020-08-01.
 */

class ArtistDiffUtilCallback : DiffUtil.ItemCallback<ArtistItem>() {
    override fun areItemsTheSame(oldItem: ArtistItem, newItem: ArtistItem): Boolean {
        return oldItem.page == newItem.page
    }

    override fun areContentsTheSame(oldItem: ArtistItem, newItem: ArtistItem): Boolean {
        return oldItem.name == newItem.name
                && oldItem.artistCoverUrl == newItem.artistCoverUrl
                && oldItem.artistHeaderUrl == newItem.artistHeaderUrl
                && oldItem.artistId == newItem.artistId
    }
}
