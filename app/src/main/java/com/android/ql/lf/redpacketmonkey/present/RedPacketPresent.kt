package com.android.ql.lf.redpacketmonkey.present

import com.android.ql.lf.redpacketmonkey.application.MyApplication

class RedPacketPresent {

    fun query(){
        MyApplication.application.daoSession.redPacketBeanDao.loadAll()
    }
}