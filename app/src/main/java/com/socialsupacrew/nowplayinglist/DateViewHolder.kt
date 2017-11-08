package com.socialsupacrew.nowplayinglist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.date_item.view.*
import java.text.DateFormat
import java.util.*

class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dateHeader: TextView = itemView.date_header

    fun bind(date: Date) = with(itemView) {
        dateHeader.text = getFormattedDate(date)
    }

    private fun getFormattedDate(date: Date): String {
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
        return dateFormatter.format(date)
    }
}
