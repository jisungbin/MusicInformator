package com.sungbin.musicinformator.paging

import androidx.paging.PageKeyedDataSource
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.model.ArtistItem
import com.sungbin.musicinformator.module.GeniusClient
import com.sungbin.musicinformator.utils.LogUtils
import com.sungbin.musicinformator.utils.ParseUtils
import com.sungbin.musicinformator.utils.manager.TypeManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * Created by SungBin on 2020-08-01.
 */



class ArtistDataSource : PageKeyedDataSource<Int, ArtistItem>() {

    val baseClient = GeniusClient.baseInstance()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ArtistItem>
    ) {
        baseClient
            .create(GeniusInterface::class.java).run {
                getSearchData(TypeManager.ARTIST, TypeManager.POPULARITY_SORT, 5, 1, "Sia")
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val item = async {
                                ParseUtils.getArtistSearchData(jsonObject)
                            }.await()

                            callback.onResult(item, 1, 2)
                        }
                    }, { throwable ->
                        LogUtils.log(throwable)
                    }, {
                        LogUtils.log("paging 데이터 로드 끝")
                    })
            }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArtistItem>
    ) {
        baseClient
            .create(GeniusInterface::class.java).run {
                getSearchData(TypeManager.ARTIST, TypeManager.POPULARITY_SORT, 5, 1, "Sia")
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val item = async {
                                ParseUtils.getArtistSearchData(jsonObject)
                            }.await()

                            callback.onResult(item, 2)
                        }
                    }, { throwable ->
                        LogUtils.log(throwable)
                    }, {
                        LogUtils.log("paging 데이터 로드 끝")
                    })
            }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArtistItem>
    ) {
        baseClient
            .create(GeniusInterface::class.java).run {
                getSearchData(TypeManager.ARTIST, TypeManager.POPULARITY_SORT, 5, 1, "Sia")
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val item = async {
                                ParseUtils.getArtistSearchData(jsonObject)
                            }.await()

                            callback.onResult(item, 1)
                        }
                    }, { throwable ->
                        LogUtils.log(throwable)
                    }, {
                        LogUtils.log("paging 데이터 로드 끝")
                    })
            }
    }
}