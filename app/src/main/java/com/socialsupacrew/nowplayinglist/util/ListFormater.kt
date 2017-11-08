package com.socialsupacrew.nowplayinglist.util

import com.socialsupacrew.nowplayinglist.data.Song
import java.util.*

fun formatSongList(songList: List<Song>): List<Any> {
    val newList: ArrayList<Any> = ArrayList()
    var previousSong = Song()

    for ((i, song) in songList.withIndex()) {
        if (isFirst(i)) {
            newList.add(song.date)
            newList.add(song)
            previousSong = song
            continue
        }

        if (haveTheSameDate(song, previousSong)) {
            newList.add(song)
            continue
        }

        newList.add(song.date)
        newList.add(song)
        previousSong = song
    }

    return newList
}

fun haveTheSameDate(song: Song, previousSong: Song): Boolean {
    val date: Calendar = toCalendar(song.date)
    val previousDate: Calendar = toCalendar(
            previousSong.date)
    return (date.get(Calendar.DAY_OF_YEAR) == previousDate.get(Calendar.DAY_OF_YEAR)
            && date.get(Calendar.YEAR) == previousDate.get(Calendar.YEAR))
}

fun toCalendar(date: Date): Calendar {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

private fun isFirst(i: Int) = i == 0

