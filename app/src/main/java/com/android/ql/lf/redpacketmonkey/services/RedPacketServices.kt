package com.android.ql.lf.redpacketmonkey.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

class RedPacketServices : IntentService("red_packet_services") {

    override fun onHandleIntent(intent: Intent?) {
        Log.e("TAG","onHandleIntent---->  ${Thread.currentThread().name}")
    }
}