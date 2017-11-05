package com.socialsupacrew.nowplayinglist

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log


class NotificationListener6 : NotificationListenerService() {

    private val nowPlayingPackageName: String = "com.google.intelligence.sense"

    override fun onCreate() {
        super.onCreate()
        Log.d("QSD", "onCreateService: ")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("QSD", "onListenerConnected: ")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Log.d("QSD", "onNotificationPosted: posted")

        if (sbn == null) {
            return
        }

        val packageName: String = sbn.packageName

//        if (packageName != nowPlayingPackageName) {
//            return
//        }

        val extras = sbn.notification.extras
        val title = extras.getString("android.title")

        Log.d("QSD", "Package:" + packageName)
        Log.d("QSD", "Title:" + title)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        Log.d("QSD", "onNotificationRemoved: remove")
    }
}
