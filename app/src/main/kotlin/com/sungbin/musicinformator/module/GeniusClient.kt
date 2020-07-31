package com.sungbin.musicinformator.module

import com.sungbin.musicinformator.utils.GeniusManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object GeniusClient {

    private val okhttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder().addHeader(
                        "Authorization",
                        GeniusManager.TOKEN
                    ).build()
                    it.proceed(request)
                }
                .build()
        }

    @Singleton
    @Named("API")
    @Provides
    fun apiInstance() = instance(true)

    @Singleton
    @Named("BASE")
    @Provides
    fun baseInstance() = instance(false)

    private fun instance(isApi: Boolean) = Retrofit.Builder()
        .apply {
            if (isApi) client(okhttpClient)
        }
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(if (isApi) GeniusManager.API_URI else GeniusManager.BASE_URI)
        .build()
}