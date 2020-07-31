package com.sungbin.musicinformator.utils

import android.content.Context
import com.sungbin.musicinformator.utils.PathManager.PER_PAGE
import com.sungbin.musicinformator.utils.PathManager.SEARCH_SORT_TYPE
import com.sungbin.musicinformator.utils.PathManager.SEARCH_TYPE
import com.sungbin.musicinformator.utils.TypeManager.ALBUM
import com.sungbin.musicinformator.utils.TypeManager.ALBUM_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.ARTIST
import com.sungbin.musicinformator.utils.TypeManager.ARTIST_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.LYRIC
import com.sungbin.musicinformator.utils.TypeManager.LYRIC_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.POPULARITY
import com.sungbin.musicinformator.utils.TypeManager.POPULARITY_SORT
import com.sungbin.musicinformator.utils.TypeManager.SONG
import com.sungbin.musicinformator.utils.TypeManager.SONG_SEARCH
import com.sungbin.musicinformator.utils.TypeManager.TITLE
import com.sungbin.musicinformator.utils.TypeManager.TITLE_SORT
import org.jetbrains.anko.defaultSharedPreferences

object QueryUtils {

    fun getSearchTypeQuery(context: Context) =
        when (context.defaultSharedPreferences.getString(SEARCH_TYPE, SONG_SEARCH)) {
            SONG_SEARCH -> SONG
            LYRIC_SEARCH -> LYRIC
            ARTIST_SEARCH -> ARTIST
            ALBUM_SEARCH -> ALBUM
            else -> SONG
        }

    fun getSortTypeQuery(context: Context) =
        when (context.defaultSharedPreferences.getString(SEARCH_SORT_TYPE, TITLE_SORT)) {
            TITLE_SORT -> TITLE
            POPULARITY_SORT -> POPULARITY
            else -> TITLE
        }

    fun getPerPageQuery(context: Context) =
        context.defaultSharedPreferences.getString(PER_PAGE, " 10").toString().toInt()
}