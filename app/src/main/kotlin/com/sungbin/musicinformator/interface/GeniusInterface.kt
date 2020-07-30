package com.sungbin.musicinformator.`interface`

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by SungBin on 2020-07-28.
 */

interface GeniusInterface {
    @GET("account")
    fun getAccountData(): Flowable<JsonObject>

    @GET("songs/{id}")
    fun getSongData(@Path("id") id: String): Flowable<JsonObject>

    @GET("search")
    fun getSearchData(@Query("q") id: String): Flowable<JsonObject>
}