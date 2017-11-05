package com.socialsupacrew.nowplayinglist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.socialsupacrew.nowplayinglist.data.Song
import kotlinx.android.synthetic.main.song_item.view.*

class ListHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    private val titleTv:TextView = itemView.songTitleTv
    private val artistTv:TextView = itemView.songArtistTv

    fun bind(song: Song) = with(itemView) {
        titleTv.text = song.title
        artistTv.text = song.artist
    }
}
