package com.android.ql.lf.redpacketmonkey.ui.activity

import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.application.MyApplication
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.test_red_packet_layout.*
import java.util.*

class TestRedPacket : BaseActivity() {

    private val redPacketDao by lazy {
        MyApplication.getInstance().redPacketDao
    }

    private var index:Long = 3

    override fun getLayoutId() = R.layout.test_red_packet_layout

    override fun initView() {
        mBtAdd.setOnClickListener {
//            val redPacketEntity = RedPacketEntity()
//            redPacketEntity.sendUserNickName = "this is new insert nick name"
//            redPacketEntity.sendUserFace = "this is new insert face"
//            redPacketEntity.newColumn = "this is new column"
//            redPacketEntity.sendTime = "${System.currentTimeMillis()}"
//            val random = Random()
//            redPacketEntity.redPacketId = random.nextInt(1000).toLong()
//            redPacketDao.insertRedPacket(redPacketEntity)
        }
        mBtUpdate.setOnClickListener {
//            val redPacketEntity = RedPacketEntity()
//            redPacketEntity.id = 4
//            redPacketEntity.sendUserNickName = "this is new update nick name"
//            redPacketEntity.sendUserFace = "this is new update face"
//            redPacketEntity.sendTime = "${System.currentTimeMillis()}"
//            redPacketEntity.redPacketId = 2
//            redPacketDao.updateRedPacket(redPacketEntity)
        }
        mBtDeleteOne.setOnClickListener {
//            val redPacketEntity = RedPacketEntity()
//            redPacketEntity.redPacketId = 2
//            redPacketDao.deleteRedPacket(redPacketEntity)
        }
        mBtDeleteAll.setOnClickListener {
            redPacketDao.deleteAll()
        }
        mBtQueryOne.setOnClickListener {
            redPacketDao.queryById(2).apply {
                mTvResult.text = Gson().toJson(this)
            }
        }
        mBtQueryAll.setOnClickListener {
            redPacketDao.queryAll().apply {
                mTvResult.text = Gson().toJson(this)
            }
        }
    }
}