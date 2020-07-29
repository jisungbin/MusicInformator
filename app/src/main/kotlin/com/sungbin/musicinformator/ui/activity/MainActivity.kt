package com.sungbin.musicinformator.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.`interface`.GeniusInterface
import com.sungbin.musicinformator.ui.dialog.ProgressDialog
import com.sungbin.musicinformator.utils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var client: Retrofit

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client
            .create(GeniusInterface::class.java).run {
                loadingDialog.show()
                getAccountData()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        LogUtils.json(it.toString())
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
}