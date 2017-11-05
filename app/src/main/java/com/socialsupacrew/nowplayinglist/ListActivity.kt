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
                    .setContentTitle("Song title by Singer")
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
    }
}
