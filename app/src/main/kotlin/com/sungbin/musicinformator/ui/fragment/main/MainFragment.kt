package com.sungbin.musicinformator.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.adapter.RecentlySongsAdapter
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    companion object {
        private val instance by lazy {
            MainFragment()
        }

        fun newInstance() = instance
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_main, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity()

        if (viewModel.songsItem.value.isNullOrEmpty())
            viewModel.initSongs(activity)
        if (viewModel.recentlySongsItem.value.isNullOrEmpty())
            viewModel.testRecentlySongs(activity)

        rv_recently_played.adapter = RecentlySongsAdapter(viewModel.recentlySongsItem.value ?: listOf(), activity)
        rv_songs.adapter = RecentlySongsAdapter(viewModel.songsItem.value ?: listOf(), activity)
    }

}