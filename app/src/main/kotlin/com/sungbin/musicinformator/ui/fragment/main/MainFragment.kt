package com.sungbin.musicinformator.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.adapter.MainSongsAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

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
            viewModel.initRecentlySongs()

        rv_recently_played.adapter =
            MainSongsAdapter(viewModel.recentlySongsItem.value ?: listOf(), activity)

        if (!viewModel.songsItem.value.isNullOrEmpty()) {
            rv_songs.visibility = View.VISIBLE
            cl_empty_songs.visibility = View.GONE
            rv_songs.adapter = MainSongsAdapter(viewModel.songsItem.value ?: listOf(), activity)
        }

    }

}