package com.sungbin.musicinformator.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.musicinformator.model.SongItem


/**
 * Created by SungBin on 2020-07-20.
 */

class RecentlySongsAdapter constructor
    (
    val items: ArrayList<SongItem>,
    val activity: Activity
) : RecyclerView.Adapter<RecentlySongsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SongItem) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(
                    R.layout.layout_bot,
                    viewGroup,
                    false
                )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        var (name, fixed, power, lastRunTime) = items[position]

        name = name.replace(".txt", "")

        viewholder.ivIcon.setImageDrawable(
            when (name) {
                "현재 상태 알림" -> ContextCompat.getDrawable(
                    activity,
                    R.drawable.lg_baseline_kakaotalk
                )
                "날씨 알림" -> ContextCompat.getDrawable(
                    activity,
                    R.drawable.lg_baseline_facebook_messenger
                )
                "끝말잇기 게임" -> ContextCompat.getDrawable(
                    activity,
                    R.drawable.lg_baseline_telegram
                )
                else -> ContextCompat.getDrawable(activity, R.drawable.lg_baseline_line)
            }
        )

        viewholder.tvName.text = name
        viewholder.swPower.isChecked = power
        viewholder.tvLastRunTime.text = lastRunTime

        viewholder.swPower.setOnCheckedChangeListener { _, checked ->
            BotPowerUtils.setPower(activity, name, checked)
        }
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    fun getItem(position: Int) = items[position]
}