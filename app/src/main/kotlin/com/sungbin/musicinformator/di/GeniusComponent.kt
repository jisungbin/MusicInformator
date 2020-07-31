package com.sungbin.musicinformator.di

import com.sungbin.musicinformator.module.GeniusClient
import com.sungbin.musicinformator.paging.artist.ArtistDataSource
import dagger.Component
import javax.inject.Singleton


/**
 * Created by SungBin on 2020-08-01.
 */

@Singleton
@Component(modules = [GeniusClient::class])
interface GeniusComponent {
    fun inject(activity: ArtistDataSource)
}