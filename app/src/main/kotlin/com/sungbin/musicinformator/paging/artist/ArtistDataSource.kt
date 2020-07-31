package com.sungbin.musicinformator.paging.artist

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.di.DaggerGeniusComponent
import com.sungbin.musicinformator.model.ArtistItem
import com.sungbin.musicinformator.utils.LogUtils
import com.sungbin.musicinformator.utils.ParseUtils
import com.sungbin.musicinformator.utils.manager.TypeManager.ARTIST
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by SungBin on 2020-08-01.
 */


class ArtistDataSource constructor(
    private val sortType: String,
    private val perPage: Int,
    private var page: Int = 1,
    private val query: String
) : PageKeyedDataSource<Int, ArtistItem>() {

    @Inject
    @Named("BASE")
    lateinit var client: Retrofit

    init {
        DaggerGeniusComponent.builder().build().inject(this)
    }

    private fun client(callback: (List<ArtistItem>) -> Unit) {
        client
            .create(GeniusInterface::class.java).run {
                LogUtils.log("client - $page")
                getSearchData(ARTIST, sortType, perPage, if (page <= 0) 1 else page, query)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val item = async {
                                ParseUtils.getArtistSearchData(jsonObject)
                            }.await()
                            callback(item)
                        }
                    }, { throwable ->
                        LogUtils.log(throwable)
                    }, {
                        LogUtils.log("paging 데이터 로드 끝")
                    })
            }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ArtistItem>
    ) {
        client {
            callback.onResult(it, 1, 2)
            LogUtils.log("called init")
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArtistItem>
    ) {
        if (page + 1 > 1) {
            page++
            client {
                callback.onResult(it, page)
                LogUtils.log("called after")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArtistItem>
    ) {
        if (page - 1 > 0) {
            page--
            client {
                callback.onResult(it, page)
                LogUtils.log("called before")
            }
        }
    }
}