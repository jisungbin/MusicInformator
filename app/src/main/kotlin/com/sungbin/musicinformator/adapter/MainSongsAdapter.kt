package com.sungbin.musicinformator.adapter

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.databinding.LayoutMainSongItemBinding
import com.sungbin.musicinformator.model.SongItem


/**
 * Created by SungBin on 2020-07-20.
 */

class MainSongsAdapter constructor
    (
    val items: List<SongItem>,
    val activity: Activity
) : RecyclerView.Adapter<MainSongsAdapter.ViewHolder>() {

    class ViewHolder(private val songItemBinding: LayoutMainSongItemBinding) :
        RecyclerView.ViewHolder(songItemBinding.root) {

        fun bindViewHolder(song: SongItem) {
            with(songItemBinding) {
                tvSongArtist.isSelected = true
                tvSongName.isSelected = true
                item = song
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.layout_main_song_item, viewGroup, false
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
                    outRect.set(0, 0, 30, 20)
                }
            }
        })
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}