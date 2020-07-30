package com.sungbin.musicinformator.utils

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.sungbin.musicinformator.model.SongItem


object SongUtils {
    private val artworkUri = Uri.parse("content://media/external/audio/albumart")
    val sampleSongItemList = arrayListOf<SongItem>(
        SongItem("다시 여기 바닷가", "싹쓰리", "https://musicmeta-phinf.pstatic.net/album/004/673/4673490.jpg?type=r32Fll&v=20200718175908"),
        SongItem("그 여름을 틀어줘", "싹쓰리", "https://musicmeta-phinf.pstatic.net/album/004/707/4707332.jpg?type=r32Fll&v=20200730114808"),
        SongItem("마리아", "화사", "https://musicmeta-phinf.pstatic.net/album/004/614/4614746.jpg?type=r32Fll&v=20200629184748"),
        SongItem("How You Like That", "BLACKPINK", "https://musicmeta-phinf.pstatic.net/album/004/613/4613637.jpg?type=r32Fll&v=20200630163708"),
        SongItem("Summer Hate", "ZICO", "https://musicmeta-phinf.pstatic.net/album/004/616/4616999.jpg?type=r32Fll&v=20200701175911"),
        SongItem("보라빛 밤", "선미", "https://musicmeta-phinf.pstatic.net/album/004/614/4614748.jpg?type=r32Fll&v=20200629184551"),
        SongItem("에잇", "아이유", "https://musicmeta-phinf.pstatic.net/album/004/550/4550593.jpg?type=r32Fll&v=20200508163228"),
        SongItem("아로하", "조정석", "https://musicmeta-phinf.pstatic.net/album/004/498/4498641.jpg?type=r32Fll&v=20200327115935"),
        SongItem("Sexual", "Neiked", "https://musicmeta-phinf.pstatic.net/album/000/662/662857.jpg?type=r204Fll&v=20200218185711"),
        SongItem("아무노래", "ZICO", "https://musicmeta-phinf.pstatic.net/album/004/413/4413282.jpg?type=r32Fll&v=20200205180913")
    )

    fun getAllAudioData(context: Context): ArrayList<SongItem> {
        val list = ArrayList<SongItem>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"
        return contentResolver.query(uri, null, selection, null, sortOrder)?.use {
            if (it.count > 0) {
                while (it.moveToNext()) {
                    val trackId =
                        it.getLong(it.getColumnIndex(MediaStore.Files.FileColumns._ID))
                    val albumId =
                        it.getLong(it.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                    val title =
                        it.getString(it.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val album =
                        it.getString(it.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artist =
                        it.getString(it.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val albumUri = getAlbumCoverUri(albumId).toString()

                    list.add(SongItem(title, artist, albumUri, trackId, albumId))
                }
            }
            list
        } ?: arrayListOf()
    }

    fun getAlbumCoverUri(
        albumId: Long
    ) = ContentUris.withAppendedId(artworkUri, albumId) as Uri

    fun getAlbumCoverBitmap(
        context: Context,
        albumId: Long,
        weight: Int,
        height: Int
    ): Bitmap? {
        val bitmapOptionsCache = BitmapFactory.Options()
        val res = context.contentResolver
        val uri = getAlbumCoverUri(albumId)
        return res.openFileDescriptor(uri, "r")?.use { fd ->
            var sampleSize = 1
            bitmapOptionsCache.inJustDecodeBounds = true
            var nextWidth = bitmapOptionsCache.outWidth shr 1
            var nextHeight = bitmapOptionsCache.outHeight shr 1
            while (nextWidth > weight && nextHeight > height) {
                sampleSize = sampleSize shl 1
                nextWidth = nextWidth shr 1
                nextHeight = nextHeight shr 1
            }
            bitmapOptionsCache.inSampleSize = sampleSize
            bitmapOptionsCache.inJustDecodeBounds = false
            var bitmap = BitmapFactory.decodeFileDescriptor(
                fd.fileDescriptor, null, bitmapOptionsCache
            )
            if (bitmap != null) {
                if (bitmapOptionsCache.outWidth != weight || bitmapOptionsCache.outHeight != height) {
                    val tmp = Bitmap.createScaledBitmap(bitmap, weight, height, true)
                    bitmap.recycle()
                    bitmap = tmp
                }
            }
            bitmap
        } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}