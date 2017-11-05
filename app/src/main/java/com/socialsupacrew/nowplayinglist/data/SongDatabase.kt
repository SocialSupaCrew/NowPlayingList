package com.socialsupacrew.nowplayinglist.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = arrayOf(Song::class), version = 1)
@TypeConverters(Converters::class)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}
