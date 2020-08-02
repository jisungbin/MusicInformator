package com.sungbin.musicinformator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sungbin.musicinformator.model.ArtistItem


/**
 * Created by SungBin on 2020-08-01.
 */

@Database(entities = [ArtistItem::class], version = 1)
abstract class ArtistDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao

    companion object {
        private var instance: ArtistDatabase? = null

        fun getInstance(context: Context): ArtistDatabase {
            if (instance == null) {
                synchronized(ArtistDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArtistDatabase::class.java,
                        "artist.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }

    fun destroyInstance() {
        instance = null
    }
}