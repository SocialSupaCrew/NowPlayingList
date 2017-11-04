package com.socialsupacrew.nowplayinglist

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class ListActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Enables Always-on
        setAmbientEnabled()
    }
}
