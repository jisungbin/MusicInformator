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

    fun testRecentlySongs(context: Context) {
        recentlySongsItem.value = arrayListOf(
            SongItem(
                "Sexual",
                "Neiked",
                "https://musicmeta-phinf.pstatic.net/album/000/662/662857.jpg?type=r204Fll&v=20200218185711",
                0L,
                0L
            ),
            SongItem(
                "Sexual",
                "Neiked",
                "https://musicmeta-phinf.pstatic.net/album/000/662/662857.jpg?type=r204Fll&v=20200218185711",
                0L,
                0L
            ),
            SongItem(
                "Sexual",
                "Neiked",
                "https://musicmeta-phinf.pstatic.net/album/000/662/662857.jpg?type=r204Fll&v=20200218185711",
                0L,
                0L
            ),
            SongItem(
                "Sexual",
                "Neiked",
                "htps://musicmeta-phinf.pstatic.net/album/000/662/662857.jpg?type=r204Fll&v=20200218185711",
                0L,
                0L
            )
        )
    }

}