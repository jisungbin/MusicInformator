package com.sungbin.musicinformator.utils

import com.google.gson.JsonObject
import com.sungbin.musicinformator.model.ArtistItem
import com.sungbin.musicinformator.model.SongItem
import com.sungbin.musicinformator.utils.extension.getString

object ParseUtils {

    fun getSongSearchData(jsonObject: JsonObject): ArrayList<SongItem> {
        val items = arrayListOf<SongItem>()
        val jsonData =
            jsonObject.getAsJsonObject("response")
                .asJsonObject["sections"]
                .asJsonArray[0]
                .asJsonObject["hits"]
                .asJsonArray

        for (element in jsonData) {
            element?.let { json ->
                val resultJson =
                    json.asJsonObject["result"].asJsonObject
                val title =
                    resultJson.getString("title")
                val artist =
                    resultJson.getAsJsonObject("primary_artist")
                        .getString("name")
                val albumUrl =
                    resultJson.getString("song_art_image_url")
                val songId = resultJson.getString("id").toInt()
                val item = SongItem(
                    title,
                    artist,
                    albumUrl,
                    songId = songId,
                    isRecentlySearched = false
                )
                items.add(item)
            }
        }
        return items
    }

    fun getArtistSearchData(jsonObject: JsonObject): ArrayList<ArtistItem> {
        val items = arrayListOf<ArtistItem>()
        val jsonData =
            jsonObject.getAsJsonObject("response")
                .asJsonObject["sections"]
                .asJsonArray[0]
                .asJsonObject["hits"]
                .asJsonArray

        for (element in jsonData) {
            element?.let { json ->
                val resultJson =
                    json.asJsonObject["result"].asJsonObject
                val name = resultJson.getString("name")
                val coverUrl = resultJson.getString("image_url")
                val headerUrl = resultJson.getString("header_image_url")
                val artistId = resultJson.getString("id").toInt()
                val item = ArtistItem(
                    name,
                    coverUrl,
                    headerUrl,
                    artistId
                )
                items.add(item)
            }
        }
        return items
    }
}