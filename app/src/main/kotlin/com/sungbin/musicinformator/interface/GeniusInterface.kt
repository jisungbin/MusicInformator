package com.sungbin.musicinformator.`interface`

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by SungBin on 2020-07-28.
 */

interface GeniusInterface {

    @GET("account")
    fun getAccountData(): Call<JSONObject>
}