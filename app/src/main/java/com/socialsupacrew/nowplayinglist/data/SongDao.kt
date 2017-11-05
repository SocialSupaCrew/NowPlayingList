package com.socialsupacrew.nowplayinglist.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface SongDao {

    @Query("SELECT * FROM song")
    fun getAllSongs(): Flowable<List<Song>>

//    @Query("SELECT * FROM song WHERE id = :arg0")
//    fun getSong(id: Long): Flowable<Song>

    @Insert
    fun insert(song: Song)

//    @Delete
//    fun delete(id: Long)
}
