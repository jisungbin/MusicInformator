package com.sungbin.musicinformator.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.adapter.SearchedSongsAdapter
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.ui.dialog.ProgressDialog
import com.sungbin.musicinformator.ui.fragment.BaseFragment
import com.sungbin.musicinformator.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.et_search
import retrofit2.Retrofit
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    companion object {
        private val instance by lazy {
            SearchFragment()
        }

        fun newInstance() = instance
    }


    @Inject
    lateinit var client: Retrofit

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog(requireActivity())
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
            viewModel.initRecentlySongs()

        rv_recently_searched.adapter =
            SearchedSongsAdapter(viewModel.recentlySongsItem.value ?: listOf(), activity)

        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        et_search.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    client
                        .create(GeniusInterface::class.java).run {
                            loadingDialog.show()
                            getSearchData(et_search.text.toString())
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ jsonObject ->
                                    jsonObject?.let {
                                        val searchedSongs = arrayListOf<SongItem>()
                                        val jsonData = it.getAsJsonObject("response").getAsJsonArray("hits")

                                        for (element in jsonData) {
                                            element?.let { json ->
                                                val resultJson = json.asJsonObject.getAsJsonObject("result")
                                                val title = resultJson["title"].toString()
                                                val artist =
                                                    resultJson.getAsJsonObject("primary_artist")["name"].toString()
                                                val albumUrl =
                                                    resultJson["song_art_image_url"].toString()
                                                val songId = resultJson["id"].toString().toInt()
                                                val item = SongItem(
                                                    title,
                                                    artist,
                                                    albumUrl,
                                                    songId = songId
                                                )
                                                searchedSongs.add(item)
                                            }
                                        }
                                        viewModel.recentlySongsItem.value = searchedSongs
                                    }
                                }, { throwable ->
                                    LogUtils.log(throwable)
                                }, {
                                    loadingDialog.close()
                                    rv_recently_searched!!.adapter?.notifyDataSetChanged()
                                })
                        }
                    return@setOnEditorActionListener true
                }
                else -> {
                    return@setOnEditorActionListener false
                }
            }
        }
    }

}