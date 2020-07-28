package com.sungbin.musicinformator.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var client: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = client.create(GeniusInterface :: class.java)
        /*Runnable {
            test.getAccentData().enqueue(object : Callback<JSONObject> {
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    toast(t.message.toString())
                }

                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    toast(response.toString())
                }
            })
        }.run()*/

        toast("run.")
    }
}