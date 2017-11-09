package com.socialsupacrew.nowplayinglist.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.socialsupacrew.nowplayinglist.R

class DetailActivity : Activity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, DetailActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
