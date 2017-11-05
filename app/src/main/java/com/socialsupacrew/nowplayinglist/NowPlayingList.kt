package com.socialsupacrew.nowplayinglist

import android.app.Application
import android.arch.persistence.room.Room
import com.socialsupacrew.nowplayinglist.data.SongDatabase

class NowPlayingList : Application() {

    companion object {
        var database: SongDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        NowPlayingList.database = Room.databaseBuilder(this, SongDatabase::class.java, "song-db").build()
    }
}
