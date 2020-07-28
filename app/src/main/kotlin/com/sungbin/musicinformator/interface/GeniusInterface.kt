package com.sungbin.musicinformator.`interface`

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers


/**
 * Created by SungBin on 2020-07-28.
 */

interface GeniusInterface {

    @Headers("Authorization: Bearer IbJtuUNSL2rYRk-ZdGkIqg9IKoyyE3KXrqOHF5FvfKlnr69jmlNwQ7u-7ydeYrjW")
    @GET("account")
    fun getAccountData(): Flowable<JsonObject>
}