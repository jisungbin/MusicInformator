package com.sungbin.musicinformator.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sungbin.musicinformator.BuildConfig
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.databinding.ActivityMainBinding
import com.sungbin.musicinformator.network.ProgressResponseBody
import com.sungbin.musicinformator.ui.dialog.ProgressDialog
import com.sungbin.musicinformator.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit.Builder

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    private lateinit var binding: ActivityMainBinding

    var text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        fun buildingLoad(progress: (Int) -> Unit) {
            retrofit
                .client(createOkHttpProgressClient(progress))
                .build()
                .create(GeniusInterface::class.java).run {
                    loadingDialog.show()
                    getAccountData()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            text = it.toString()
                            binding.invalidateAll()
                        }, { throwable ->
                            LogUtils.log(throwable)
                        }, {
                            LogUtils.log("작업 끝")
                            loadingDialog.close()
                            //loadingDialog.setMessage("작업 끝")
                            //loadingDialog.updateTitle("작업 끝")
                        })
                }
        }

        buildingLoad {
            Log.d("ZZZZ", "$it / 100")
        }
    }

    private fun createOkHttpProgressClient(progress: (Int) -> Unit): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        builder.addInterceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            originalResponse.newBuilder()
                .body(originalResponse.body?.let {
                    ProgressResponseBody(
                        it,
                        progress
                    )
                })
                .build()
        }
        return builder.build()
    }

}