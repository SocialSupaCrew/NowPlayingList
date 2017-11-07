package com.socialsupacrew.nowplayinglist

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.socialsupacrew.nowplayinglist.data.Song
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListAdapter(
    private val context: Context,
    private var items: List<Song>) : RecyclerView.Adapter<ListHolder>(), FilterSwipeDismissListener {

    override fun onBindViewHolder(holder: ListHolder?, position: Int) {
        holder?.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return ListHolder(v)
    }

    override fun onItemDismiss(position: Int) {
        Single.fromCallable { NowPlayingList.database?.songDao()?.delete(getItem(position)) }
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
        this.items = songList
        notifyDataSetChanged()
    }

    fun notifySongInserted(songList: List<Song>) {
        this.items = songList
        notifyItemInserted(0)
    }

    fun notifySongRemoved(songList: List<Song>, position: Int) {
        this.items = songList
        notifyItemRemoved(position)
    }
}
