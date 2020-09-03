package com.sungbin.musicinformator.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.musicinformator.utils.SongUtils

class SearchViewModel : ViewModel() {

    val items: MutableLiveData<List<Any>> = MutableLiveData()

    fun initRecentlySongs() {
        items.value = SongUtils.sampleSongItemList
    }

}