package com.socialsupacrew.nowplayinglist

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.socialsupacrew.nowplayinglist.data.Song
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class NotificationListener12 : NotificationListenerService() {

    private val nowPlayingPackageName: String = "com.google.intelligence.sense"

    override fun onCreate() {
        super.onCreate()
        Log.d("QSD", "onCreateService: ")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("QSD", "onListenerConnected: ")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.d("QSD", "onNotificationPosted: posted")

        if (!isValidNotification(sbn)) {
            return
        }

        val notificationTitle = getNotificationTitle(sbn)
        // TODO: find solution when song title or artist contain the word separator
        addSong(getSongTitle(notificationTitle), getSongArtist(notificationTitle))
    }

    private fun isValidNotification(sbn: StatusBarNotification?): Boolean {
        if (sbn == null) {
            return false
        }

        val packageName: String = sbn.packageName
        val extras = sbn.notification.extras
        val notificationTitle = extras.getString("android.title")

        return packageName == nowPlayingPackageName && notificationTitle != null
    }

    private fun getNotificationTitle(sbn: StatusBarNotification): String {
        val extras = sbn.notification.extras
        return extras.getString("android.title")
    }

    private fun addSong(title: String, artist: String) {
        val song = Song(0, title, artist, Date())

        Single.fromCallable { NowPlayingList.database?.songDao()?.insert(song) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        refreshList()
    }

    private fun refreshList() {
        val intent = Intent()
        intent.action = ListActivity.BROADCAST_SONG_INSERTED
        sendBroadcast(intent)
    }

    private fun getSongTitle(notificationTitle: String) =
        Arrays.asList(notificationTitle.split(NowPlayingList.WORD_SONG_SEPARATOR!!))[0][0]

    private fun getSongArtist(notificationTitle: String) =
        Arrays.asList(notificationTitle.split(NowPlayingList.WORD_SONG_SEPARATOR!!))[0][1]
}
