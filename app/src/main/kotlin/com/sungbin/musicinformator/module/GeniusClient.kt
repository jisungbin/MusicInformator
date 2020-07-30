package com.sungbin.musicinformator.module

import com.sungbin.musicinformator.utils.GeniusManager
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

    private val okhttpClient: OkHttpClient get(){
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder().addHeader(
                    "Authorization",
                    "Bearer FuhUd_YEfku02ZyxH7QL1gN3q3hERKPK916sZT2ljmPg2I3KkETlaTvnOGQQxaTM"
                ).build()
                it.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun instance() = Retrofit.Builder()
        .client(okhttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(GeniusManager.BASE_URI)
        .build()
}