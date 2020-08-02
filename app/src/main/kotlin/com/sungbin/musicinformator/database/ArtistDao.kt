package com.sungbin.musicinformator.database

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
    @Query("SELECT * FROM artist where page = :page")
    fun getItem(page: Int): List<ArtistItem>

    @Insert(onConflict = REPLACE)
    fun insert(artists: List<ArtistItem>/*, page: Int*/)
}