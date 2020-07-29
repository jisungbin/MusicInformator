package com.sungbin.musicinformator.module

import com.sungbin.musicinformator.BuildConfig
import com.sungbin.musicinformator.network.ProgressResponseBody
import com.sungbin.musicinformator.utils.GeniusUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object GeniusClient {

    @Singleton
    @Provides
    fun instance() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(GeniusUtils.BASE_URI)
}