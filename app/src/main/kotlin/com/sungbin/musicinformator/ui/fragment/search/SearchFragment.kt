package com.sungbin.musicinformator.ui.fragment.search

import android.annotation.SuppressLint
import android.app.Service
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.adapter.ArtistPagingAdapter
import com.sungbin.musicinformator.model.ArtistItem
import com.sungbin.musicinformator.paging.artist.ArtistDataSource
import com.sungbin.musicinformator.ui.activity.MainActivity.Companion.database
import com.sungbin.musicinformator.ui.dialog.ProgressDialog
import com.sungbin.musicinformator.ui.dialog.SearchOptionBottomDialog
import com.sungbin.musicinformator.util.LogUtils
import com.sungbin.musicinformator.util.ParseUtils
import com.sungbin.musicinformator.util.QueryUtils
import com.sungbin.musicinformator.util.extension.plusAssign
import com.sungbin.musicinformator.util.extension.setEndDrawableClickEvent
import com.sungbin.musicinformator.util.manager.TypeManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
@WithFragmentBindings
class SearchFragment : Fragment() {

    companion object {
        /*private val instance by lazy {
            SearchFragment() //for Hilt
        }*/

        fun instance() = SearchFragment()
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

    private val pagingAdapter by lazy {
        ArtistPagingAdapter()
    }

    private val imm by lazy {
        requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private var perPage = 0
    private var sortType = ""
    private var searchType = ""
    private var query = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_search, container, false)!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        retainInstance = false

        val activity = requireActivity()

        if (viewModel.items.value.isNullOrEmpty())
            viewModel.initRecentlySongs()

        rv_recently_searched.adapter = pagingAdapter

        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        et_search.setEndDrawableClickEvent {
            val bottomSheetDialog = SearchOptionBottomDialog.instance()
            bottomSheetDialog.show(childFragmentManager, "검색 설정")
        }
        et_search.setOnEditorActionListener { _, actionId, _ ->
            imm.hideSoftInputFromWindow(et_search.windowToken, RESULT_UNCHANGED_SHOWN)
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    baseClient
                        .create(GeniusInterface::class.java).run {
                            loadingDialog.show()

                            perPage = 0
                            sortType = ""
                            searchType = ""
                            query = et_search.text.toString()

                            context?.let {
                                perPage = QueryUtils.getPerPageQuery(it)
                                sortType = QueryUtils.getSortTypeQuery(it)
                                searchType = QueryUtils.getSearchTypeQuery(it)
                            }

                            getSearchData(searchType, sortType, perPage, 1, query)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ jsonObject ->
                                    CoroutineScope(Dispatchers.Main).launch {
                                        viewModel.items.value = when (searchType) {
                                            TypeManager.SONG -> async {
                                                ParseUtils.getSongSearchData(jsonObject)
                                            }.await()
                                            TypeManager.ARTIST -> async {
                                                ParseUtils.getArtistSearchData(jsonObject)
                                            }.await()
                                            else -> arrayListOf()
                                        }
                                    }
                                }, { throwable ->
                                    LogUtils.log(throwable)
                                    loadingDialog.setError(throwable)
                                }, {
                                    tv_searched_songs += getString(R.string.search_result)
                                    val config = PagedList.Config.Builder()
                                        .setPageSize(perPage)
                                        .setEnablePlaceholders(true)
                                        .build()

                                    val liveData = initializedPagedListBuilder(config).build()

                                    liveData.observe(
                                        activity,
                                        Observer<PagedList<ArtistItem>> { pagedList ->
                                            pagingAdapter.submitList(pagedList)
                                        })

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

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, ArtistItem> {
        val dataSourceFactory = object : DataSource.Factory<Int, ArtistItem>() {
            override fun create(): DataSource<Int, ArtistItem> {
                return ArtistDataSource(
                    sortType,
                    perPage,
                    query,
                    database
                )
            }
        }
        return LivePagedListBuilder<Int, ArtistItem>(dataSourceFactory, config)
    }

}