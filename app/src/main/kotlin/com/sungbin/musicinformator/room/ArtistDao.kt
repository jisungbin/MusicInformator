package com.sungbin.musicinformator.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sungbin.musicinformator.model.ArtistItem


/**
 * Created by SungBin on 2020-08-01.
 */

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artist")
    fun getAll(): List<ArtistItem>

    @Insert(onConflict = REPLACE)
    fun insert(artists: List<ArtistItem>)

    @Query("DELETE from artist")
    fun deleteAll()
}