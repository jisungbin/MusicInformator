package com.sungbin.musicinformator.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.ui.fragment.BaseFragment

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

        val context = requireContext()

        if (viewModel.songsItem.value.isNullOrEmpty())
            viewModel.initSongs(context)
        if (viewModel.recentlySongsItem.value.isNullOrEmpty())
            viewModel.testRecentlySongs(context)


    }

}