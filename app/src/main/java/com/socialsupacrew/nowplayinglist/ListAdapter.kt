package com.socialsupacrew.nowplayinglist

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.socialsupacrew.nowplayinglist.data.Song
import com.socialsupacrew.nowplayinglist.util.formatSongList
import com.socialsupacrew.nowplayinglist.util.inflate
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListAdapter(
    private val context: Context,
    private var items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FilterSwipeDismissListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Companion.DATE -> {
                val v = parent.inflate(R.layout.date_item)
                DateViewHolder(v)
            }
            Companion.SONG -> {
                val v = parent.inflate(R.layout.song_item)
                SongViewHolder(v)
            }
            else -> {
                throw Exception("ViewType: $viewType is not supported")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemViewType: Int? = holder?.itemViewType
        when (itemViewType) {
            Companion.DATE -> {
                (holder as DateViewHolder).bind(getItem(position) as Date)
            }
            Companion.SONG -> {
                (holder as SongViewHolder).bind(getItem(position) as Song)
            }
            else -> {
                throw Exception("ViewType: $itemViewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) is Song) {
            return Companion.SONG
        } else if (getItem(position) is Date) {
            return Companion.DATE
        }
        return -1
    }

    override fun onItemDismiss(position: Int) {
        Single.fromCallable { NowPlayingList.database?.songDao()?.delete(getItem(position) as Song) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        refreshList(position)
    }

    private fun refreshList(position: Int) {
        val intent = Intent()
        intent.action = ListActivity.BROADCAST_SONG_DELETED
        intent.putExtra(ListActivity.SONG_POSITION, position)
        context.sendBroadcast(intent)
    }

    private fun getItem(position: Int) = items[position]

    fun notifyData(songList: List<Song>) {
        this.items = formatSongList(songList)
        notifyDataSetChanged()
    }

    fun notifySongInserted(songList: List<Song>) {
        this.items = formatSongList(songList)
        notifyItemInserted(0)
    }

    fun notifySongRemoved(songList: List<Song>, position: Int) {
        this.items = formatSongList(songList)
        notifyItemRemoved(position)
    }

    companion object {
        val DATE: Int = 0
        val SONG: Int = 1
    }
}
