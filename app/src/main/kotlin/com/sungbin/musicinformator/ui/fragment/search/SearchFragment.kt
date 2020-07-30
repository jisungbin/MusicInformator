package com.sungbin.musicinformator.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.adapter.SearchedSongsAdapter
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.ui.dialog.ProgressDialog
import com.sungbin.musicinformator.ui.fragment.BaseFragment
import com.sungbin.musicinformator.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_searched_song.*
import retrofit2.Retrofit
import javax.inject.Inject


@AndroidEntryPoint
@WithFragmentBindings
class SearchFragment : BaseFragment() {

    companion object {
        private val instance by lazy {
            SearchFragment()
        }

        fun instance() = instance
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

        retainInstance = false

        val activity = requireActivity()

        if (viewModel.songsItem.value.isNullOrEmpty())
            viewModel.initSongs(activity)
        if (viewModel.recentlySongsItem.value.isNullOrEmpty())
            viewModel.initRecentlySongs()

        viewModel.recentlySongsItem.observe(viewLifecycleOwner, Observer {
            rv_recently_searched.adapter =
                SearchedSongsAdapter(it ?: listOf(), activity)
        })

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
                                        val jsonData =
                                            it.getAsJsonObject("response").getAsJsonArray("hits")

                                        for (element in jsonData) {
                                            element?.let { json ->
                                                val resultJson =
                                                    json.asJsonObject.getAsJsonObject("result")
                                                val title =
                                                    resultJson["title"].toString().replace("\"", "")
                                                val artist =
                                                    resultJson.getAsJsonObject("primary_artist")["name"].toString()
                                                        .replace("\"", "")
                                                val albumUrl =
                                                    resultJson["song_art_image_url"].toString()
                                                        .replace("\"", "")
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
                                    tv_searched_songs.text = getString(R.string.search_result)
                                    iv_remove.visibility = View.INVISIBLE
                                    loadingDialog.close()
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