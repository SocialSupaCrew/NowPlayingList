package com.socialsupacrew.nowplayinglist

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.socialsupacrew.nowplayinglist.data.Song
import com.socialsupacrew.nowplayinglist.util.SongTouchHelperCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : Activity() {

//    private val manager: NotificationManager by lazy {
//        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    }

    private var idNotification: Int = 0
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListAdapter
    private var emptySongList: List<Song> = ArrayList()

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BROADCAST_SONG_INSERTED -> handleSongInserted()
                BROADCAST_SONG_DELETED -> handleSongRemoved(intent.extras.getInt(SONG_POSITION))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (!NowPlayingList.isLanguageCompatible) {
            songsRecyclerView.visibility = View.GONE
            notCompatibleText.visibility = View.VISIBLE
            return
        }

        registerBroadcastReceiver()

        val dividerDrawable = getDrawable(R.drawable.divider)
        val dividerItemDecoration = ListItemDecoration(dividerDrawable)
        songsRecyclerView.addItemDecoration(dividerItemDecoration)

        linearLayoutManager = LinearLayoutManager(this)
        songsRecyclerView.layoutManager = linearLayoutManager

        listAdapter = ListAdapter(this, emptySongList)
        songsRecyclerView.adapter = listAdapter

        val callback = SongTouchHelperCallback(listAdapter,
                this)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(songsRecyclerView)

        fetchSongs()

//        val notificationBtn: Button = findViewById(R.id.notificationButton)
//        val notificationSettingsBtn: Button = findViewById(R.id.notificationSettingsButton)
//
//        val notificationChannel = NotificationChannel("default",
//                "default channel",
//                NotificationManager.IMPORTANCE_DEFAULT)
//        notificationChannel.lightColor = Color.GREEN
//        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
//        manager.createNotificationChannel(notificationChannel)
//
//        notificationBtn.setOnClickListener {
//
//            val notificationBuilder = Notification.Builder(this, "default")
//                .setContentTitle("Song title $idNotification par Singer $idNotification")
//                .setContentText(null)
//                .setSmallIcon(android.R.drawable.stat_notify_chat)
//                .setAutoCancel(true)
//
//            manager.notify(idNotification, notificationBuilder.build())
//            idNotification++
//        }
//
//        notificationSettingsBtn.setOnClickListener {
//            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
//            startActivity(intent)
//        }
    }

    override fun onDestroy() {
        unregisterBroadcastReceiver()
        super.onDestroy()
    }

    private fun fetchSongs() {
        NowPlayingList.database?.songDao()?.getAllSongs()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.first(emptySongList)
            ?.subscribe { songList ->
                displaySongs(songList)
            }
    }

    private fun displaySongs(songList: List<Song>) {
        listAdapter.notifyData(songList)
    }

    private fun handleSongInserted() {
        NowPlayingList.database?.songDao()?.getAllSongs()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.first(emptySongList)
            ?.subscribe { songList ->
                // TODO: snackbar to scroll to top
                listAdapter.notifySongInserted(songList)
            }
    }

    private fun handleSongRemoved(position: Int) {
        NowPlayingList.database?.songDao()?.getAllSongs()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.first(emptySongList)
            ?.subscribe { songList ->
                // TODO: snackbar to undo
                listAdapter.notifySongRemoved(songList, position)
            }
    }

    private fun registerBroadcastReceiver() {
        registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_SONG_INSERTED))
        registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_SONG_DELETED))
    }

    private fun unregisterBroadcastReceiver() {
        unregisterReceiver(broadCastReceiver)
    }

    companion object {
        val BROADCAST_SONG_DELETED: String = "songDeleted"
        val BROADCAST_SONG_INSERTED: String = "songInserted"
        val SONG_POSITION: String = "songPosition"
    }
}
