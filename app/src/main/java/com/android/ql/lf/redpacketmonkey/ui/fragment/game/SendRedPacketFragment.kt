package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.GroupBean
import com.android.ql.lf.redpacketmonkey.data.room.RedPacketEntity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.getTextString
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import com.android.ql.lf.redpacketmonkey.utils.isMoney
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_send_red_packet_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class SendRedPacketFragment : BaseNetWorkingFragment() {

    companion object {
        const val GROUP_INFO_FLAG = "groupInfo"
    }

    private val groupInfo by lazy {
        arguments!!.getSerializable(GROUP_INFO_FLAG) as GroupBean
    }

    private var minCount: Double? = null
    private var maxCount: Double? = null

    private val noMoneyDialogFragment by lazy {
        NoMoneyDialogFragment()
    }


    override fun getLayoutId() = R.layout.fragment_send_red_packet_layout

    override fun initView(view: View?) {
        mBtSendRedPacketSend.setOnClickListener {
            if (minCount == null || maxCount == null) {
                return@setOnClickListener
            }
            if (mEtSendRedPacketMoney.isEmpty()) {
                toast("请输入红包金额")
                return@setOnClickListener
            }
            if (!mEtSendRedPacketMoney.isMoney()) {
                toast("请输入合法的金额")
                return@setOnClickListener
            }
            if (mEtSendRedPacketMoney.getTextString().toFloat() < minCount!! || mEtSendRedPacketMoney.getTextString().toFloat() > maxCount!!) {
                toast("请输入$minCount - $maxCount 之间的金额")
                return@setOnClickListener
            }
            if (mEtSendRedPacketMine.isEmpty()) {
                toast("请输入红包雷数")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x1, RequestParamsHelper.getSendRedPacketParam(groupInfo.group_id!!.toString(), mEtSendRedPacketMoney.getTextString(), mEtSendRedPacketMine.getTextString()))
        }
        mPresent.getDataByPost(0x0, RequestParamsHelper.getGroupInfoParam(groupInfo.group_id!!.toString()))
    }

    override fun onRequestStart(requestID: Int) {
        when (requestID) {
            0x0 -> getFastProgressDialog("正在加载信息……")
            0x1 -> getFastProgressDialog("正在发红包……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x1) {
            val json = JSONObject(result.toString())
            if (json.optInt(RESULT_CODE) == 300 && json.optString(MSG_FLAG) == "您得余额不足!") {
                noMoneyDialogFragment.show(fragmentManager, "no_money_dialog")
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x0 -> {
                if (checkedObjType(obj)) {
                    minCount = (obj as JSONObject).optDouble("group_sum_min")
                    maxCount = obj.optDouble("group_sum_max")
                    mBtSendRedPacketSend.isEnabled = true
                    mTvSendRedPacketCount.text = "${(obj as JSONObject).optString("group_cou")}个"
                    mTvSendRedPacketAgain.text = "游戏倍数：${obj.optString("group_again")}"
                    mTvSendRedPacketRange.text = "红包发布范围：$minCount - $maxCount"
                    mTvSendRedPacketBackTime.text = "未领取的红包将于${obj.optString("group_quit_times")}分钟后发起退款"
                }
            }
            0x1 -> {
                if (checkedObjType(obj)) {
                    val redPacketEntity = Gson().fromJson((obj as JSONObject).toString(), RedPacketEntity::class.java)
                    redPacketEntity.itemType = RedPacketEntity.SEND_RED_PACKET
                    finish()
                } else {
                    toast("红包发送失败")
                }
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> "信息加载失败"
            0x1 -> "红包发送失败"
            else -> "未知错误"
        }
    }

}