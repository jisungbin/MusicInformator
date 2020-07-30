package com.sungbin.musicinformator.adapter

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.databinding.LayoutSearchedSongBinding
import com.sungbin.musicinformator.databinding.LayoutSongItemBinding
import com.sungbin.musicinformator.model.SongItem


/**
 * Created by SungBin on 2020-07-20.
 */

class SearchedSongsAdapter constructor(
    val items: List<SongItem>,
    val activity: Activity
) : RecyclerView.Adapter<SearchedSongsAdapter.ViewHolder>() {

    class ViewHolder(private val songItemBinding: LayoutSearchedSongBinding) :
        RecyclerView.ViewHolder(songItemBinding.root) {

        init {
            songItemBinding.lifecycleOwner = songItemBinding.root.context as LifecycleOwner
        }

        fun bindViewHolder(item: SongItem) {
            songItemBinding.tvSongArtist.isSelected = true
            songItemBinding.tvSongName.isSelected = true
            songItemBinding.songItem = item
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.layout_searched_song, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() { //아이템 간격
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount) {
                    outRect.set(0, 0, 0, 30)
                }
            }
        })
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    fun getItem(position: Int) = items[position]
}