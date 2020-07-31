package com.sungbin.musicinformator.ui.fragment.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.adapter.SearchedSongsAdapter
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.ui.dialog.ProgressDialog
import com.sungbin.musicinformator.ui.dialog.SearchOptionBottomDialog
import com.sungbin.musicinformator.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
@WithFragmentBindings
class SearchFragment : Fragment() {

    companion object {
        private val instance by lazy {
            SearchFragment()
        }

        fun instance() = instance
    }

    @Named("API")
    @Inject
    lateinit var apiClient: Retrofit

    @Named("BASE")
    @Inject
    lateinit var baseClient: Retrofit

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog(requireActivity())
    }

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_search, container, false)!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        retainInstance = false

        val activity = requireActivity()

        if (viewModel.songsItem.value.isNullOrEmpty())
            viewModel.initRecentlySongs()

        viewModel.songsItem.observe(viewLifecycleOwner, Observer {
            rv_recently_searched.adapter =
                SearchedSongsAdapter(it ?: listOf(), activity)
        })

        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        et_search.setEndDrawableClickEvent {
            val bottomSheetDialog = SearchOptionBottomDialog.instance()
            bottomSheetDialog.show(childFragmentManager, "검색 설정")
        }
        et_search.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    baseClient
                        .create(GeniusInterface::class.java).run {
                            loadingDialog.show()

                            var perPage = 0
                            var sortType = ""
                            var searchType = ""

                            context?.let {
                                perPage = QueryUtils.getPerPageQuery(it)
                                sortType = QueryUtils.getSortTypeQuery(it)
                                searchType = QueryUtils.getSearchTypeQuery(it)
                            }

                            val query = et_search.text.toString()

                            getSearchData(searchType, sortType, perPage, 1, query)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ jsonObject ->

                                    jsonObject?.let {
                                        val searchedSongs = arrayListOf<SongItem>()
                                        val jsonData =
                                            it.getAsJsonObject("response")
                                                .asJsonObject["sections"]
                                                .asJsonArray[0]
                                                .asJsonObject["hits"]
                                                .asJsonArray

                                        for (element in jsonData) {
                                            element?.let { json ->
                                                val resultJson =
                                                    json.asJsonObject["result"].asJsonObject
                                                val title =
                                                    resultJson.getString("title")
                                                val artist =
                                                    resultJson.getAsJsonObject("primary_artist")
                                                        .getString("name")
                                                val albumUrl =
                                                    resultJson.getString("song_art_image_url")
                                                val songId = resultJson.getString("id").toInt()
                                                val item = SongItem(
                                                    title,
                                                    artist,
                                                    albumUrl,
                                                    songId = songId,
                                                    isRecentlySearched = false
                                                )
                                                searchedSongs.add(item)
                                            }
                                        }
                                        viewModel.songsItem.value = searchedSongs
                                    }
                                }, { throwable ->
                                    LogUtils.log(throwable)
                                    loadingDialog.setError(throwable)
                                }, {
                                    tv_searched_songs += getString(R.string.search_result)
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