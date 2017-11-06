package com.socialsupacrew.nowplayinglist

import android.app.Application
import android.arch.persistence.room.Room
import com.socialsupacrew.nowplayinglist.data.SongDatabase
import com.socialsupacrew.nowplayinglist.util.WordSongSeparator
import java.util.*

class NowPlayingList : Application() {

    companion object {
        var isLanguageCompatible: Boolean = true
        var database: SongDatabase? = null
        var WORD_SONG_SEPARATOR: String? = null
    }

    override fun onCreate() {
        super.onCreate()

        try {
            WORD_SONG_SEPARATOR = getWordSongSeparator()
        } catch (exception: IllegalArgumentException) {
            isLanguageCompatible = false
            return
        }

        NowPlayingList.database = Room.databaseBuilder(this, SongDatabase::class.java, "song-db").build()
    }

    private fun getDeviceLanguage() = Locale.getDefault().isO3Language.toUpperCase()

    private fun getWordSongSeparator(): String? = WordSongSeparator.valueOf(getDeviceLanguage()).toString()
}
