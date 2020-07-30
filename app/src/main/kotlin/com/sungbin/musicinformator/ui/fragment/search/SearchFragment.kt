package com.sungbin.musicinformator.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.adapter.RecentlySongsAdapter
import com.sungbin.musicinformator.adapter.SearchedSongsAdapter
import com.sungbin.musicinformator.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    companion object {
        private val instance by lazy {
            SearchFragment()
        }

        fun newInstance() = instance
    }

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_search, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity()

        if (viewModel.songsItem.value.isNullOrEmpty())
            viewModel.initSongs(activity)
        if (viewModel.recentlySongsItem.value.isNullOrEmpty())
            viewModel.testRecentlySongs(activity)

        rv_recently_searched.adapter =
            SearchedSongsAdapter(viewModel.recentlySongsItem.value ?: listOf(), activity)
    }

}