package com.socialsupacrew.nowplayinglist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.socialsupacrew.nowplayinglist.data.Song
import kotlinx.android.synthetic.main.song_item.view.*
import java.text.DateFormat
import java.util.*

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTv: TextView = itemView.songTitleTv
    private val artistTv: TextView = itemView.songArtistTv
    private val hourTv: TextView = itemView.listeningHour

    fun bind(song: Song) = with(itemView) {
        titleTv.text = song.title
        artistTv.text = song.artist
        hourTv.text = getHourFromDate(song.date)
    }

    private fun getHourFromDate(date: Date): String {
        val dateFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
        return dateFormatter.format(date)
    }
}
