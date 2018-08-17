package com.android.ql.lf.redpacketmonkey.ui.fragment.packet

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import kotlinx.android.synthetic.main.fragment_mine_packet_layout.*
import org.json.JSONObject
import java.math.BigDecimal

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
            BalanceListFragment.startRecord(mContext, mTvMinePacketRechargeRecord.text.toString(), 1)
        }

        mTvMinePacketCrashRecord.setOnClickListener {
            BalanceListFragment.startRecord(mContext, mTvMinePacketCrashRecord.text.toString(), 2)
        }

        mTvMinePacketBalanceRecord.setOnClickListener {
            BalanceListFragment.startRecord(mContext, mTvMinePacketBalanceRecord.text.toString(), 3)
        }
        mPresent.getDataByPost(0x0,RequestParamsHelper.getCountLog(1))
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在加载……")
    }

    override fun showFailMessage(requestID: Int): String {
        return "数据加载失败"
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        if (checkedObjType(obj)){
            UserInfo.getInstance().setMoney_sum_cou((obj as JSONObject).optDouble("sumcou"))
            UserInfoLiveData.postUserInfo()
            mTvMinePacketMoneyCount.text = "${UserInfo.getInstance().money_sum_cou}"
        }
    }
}