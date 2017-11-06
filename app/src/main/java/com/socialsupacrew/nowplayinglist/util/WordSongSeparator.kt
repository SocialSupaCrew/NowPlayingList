package com.socialsupacrew.nowplayinglist.util

// TODO: Improve the word song separator detection
enum class WordSongSeparator(private val wordSeparator: String) {
    ENG(" by "),
    FRA(" par ");

    override fun toString() = wordSeparator
}
