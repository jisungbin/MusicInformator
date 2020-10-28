package com.sungbin.musicinformator.ui.fragment.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.utils.SongUtils

class MainViewModel : ViewModel() {

    val recentlySongsItem: MutableLiveData<List<SongItem>> = MutableLiveData()
    val songsItem: MutableLiveData<List<SongItem>> = MutableLiveData()

    fun initSongs(context: Context) {
        songsItem.value = SongUtils.getAllAudioData(context)
    }

    fun initRecentlySongs() {
        recentlySongsItem.value = SongUtils.sampleSongItemList
    }
}
