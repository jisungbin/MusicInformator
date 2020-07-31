package com.sungbin.musicinformator.paging

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.databinding.LayoutArtistItemBinding
import com.sungbin.musicinformator.model.ArtistItem


/**
 * Created by SungBin on 2020-08-01.
 */

class ArtistPagingAdapter : PagedListAdapter<ArtistItem, ArtistPagingAdapter.ViewHolder>(ArtistDiffUtilCallback())  {

    class ViewHolder(private val artistItemBinding: LayoutArtistItemBinding) :
        RecyclerView.ViewHolder(artistItemBinding.root) {

        fun bindViewHolder(item: ArtistItem) {
            artistItemBinding.tvArtistName.isSelected = true
            artistItemBinding.item = item
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_artist_item, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        getItem(position)?.let {
            viewholder.bindViewHolder(it)
        }
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

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}