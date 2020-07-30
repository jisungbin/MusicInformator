package com.sungbin.musicinformator.ui.fragment.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.utils.SongUtils

class SearchViewModel : ViewModel() {

    val recentlySongsItem: MutableLiveData<List<SongItem>> = MutableLiveData()
    val songsItem: MutableLiveData<List<SongItem>> = MutableLiveData()

    fun initSongs(context: Context) {
        songsItem.value = SongUtils.getAllAudioData(context)
    }

    fun initRecentlySongs() {
        recentlySongsItem.apply {
            val items = arrayListOf<SongItem>()
            for (i in 0..10) items.add(SongUtils.getTestSongItem())
            value = items
        }
    }

}