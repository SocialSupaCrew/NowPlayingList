package com.socialsupacrew.nowplayinglist

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ListActivity : Activity() {

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var idNotification: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

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
            Log.d("QSD", "onClick: ")
        }

        notificationSettingsBtn.setOnClickListener {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
        displaySongs()
    }

    private fun displaySongs() {
        NowPlayingList.database?.songDao()?.getAllSongs()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { songsList ->
                    for (song in songsList) {
                        Log.d("QSD", "Song: ")
                        Log.d("QSD", "title:" + song.title)
                        Log.d("QSD", "artist:" + song.artist)
                        Log.d("QSD", "id:" + song.id)
                        Log.d("QSD", "date:" + song.date)
                        Log.d("QSD", "-----------------------")
                    }
                }
    }
}
