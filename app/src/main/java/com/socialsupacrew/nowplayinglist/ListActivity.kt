package com.socialsupacrew.nowplayinglist

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Button
import com.socialsupacrew.nowplayinglist.data.Song
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : Activity() {

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var idNotification: Int = 0
    private var emptySongList: ArrayList<Song> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        songsRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        linearLayoutManager = LinearLayoutManager(this)
        songsRecyclerView.layoutManager = linearLayoutManager

        displaySongs()

        val notificationBtn: Button = findViewById(R.id.notificationButton)
        val notificationSettingsBtn: Button = findViewById(R.id.notificationSettingsButton)

        val notificationChannel = NotificationChannel("default",
                "default channel",
                NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager.createNotificationChannel(notificationChannel)

        notificationBtn.setOnClickListener {

            val notificationBuilder = Notification.Builder(this, "default")
                    .setContentTitle("Song title $idNotification by Singer $idNotification")
                    .setContentText(null)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setAutoCancel(true)

            manager.notify(idNotification, notificationBuilder.build())
            idNotification++
        }

        notificationSettingsBtn.setOnClickListener {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }

    private fun displaySongs() {
        NowPlayingList.database?.songDao()?.getAllSongs()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { songList ->
                    listAdapter = ListAdapter(songList)
                    songsRecyclerView.adapter = listAdapter

                    val callback = FilterTouchHelperCallback(listAdapter, this)
                    val itemTouchHelper = ItemTouchHelper(callback)
                    itemTouchHelper.attachToRecyclerView(songsRecyclerView)
                }
    }
}
