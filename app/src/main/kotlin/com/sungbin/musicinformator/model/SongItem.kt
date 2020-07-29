package com.sungbin.musicinformator.model


/**
 * Created by SungBin on 2020-06-21.
 */

data class SongItem(
    var name: String,
    var artist: String,
    var albumImageUrl: String?,
    var trackId: Long,
    var albumId: Long)