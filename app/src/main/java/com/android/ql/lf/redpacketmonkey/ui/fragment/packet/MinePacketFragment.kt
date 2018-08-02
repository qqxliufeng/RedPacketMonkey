package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_packet_layout.*

class MinePacketFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_mine_packet_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setSupportActionBar(mTlMinePacket)
        (mContext as FragmentContainerActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTlMinePacket.setNavigationOnClickListener { finish() }
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlMinePacket.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight

        mTvMinePacketMoneyCount.text = "${UserInfo.getInstance().money_sum_cou.toFloat()}"

        mTvMinePacketRedPacketRecord.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("红包记录").setNeedNetWorking(false).setClazz(RedPacketRecordFragment::class.java).start()
        }

        mTvMinePacketRechargeRecord.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle(mTvMinePacketRechargeRecord.text.toString()).setNeedNetWorking(true).setClazz(BalanceListFragment::class.java).start()
        }

        mTvMinePacketCrashRecord.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle(mTvMinePacketCrashRecord.text.toString()).setNeedNetWorking(true).setClazz(BalanceListFragment::class.java).start()
        }

        mTvMinePacketBalanceRecord.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle(mTvMinePacketBalanceRecord.text.toString()).setNeedNetWorking(true).setClazz(BalanceListFragment::class.java).start()
        }
    }
}