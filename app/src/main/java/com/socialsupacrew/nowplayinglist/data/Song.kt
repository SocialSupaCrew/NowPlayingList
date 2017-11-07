package com.socialsupacrew.nowplayinglist.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "song")
data class Song constructor(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "artist")
        var artist: String = "",
        @ColumnInfo(name = "date")
        var date: Date = Date())
