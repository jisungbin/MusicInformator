package com.sungbin.musicinformator.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.utils.SongUtils

class SearchViewModel : ViewModel() {

    val songsItem: MutableLiveData<List<SongItem>> = MutableLiveData()

    fun initRecentlySongs() {
        songsItem.value = SongUtils.sampleSongItemList
    }

}