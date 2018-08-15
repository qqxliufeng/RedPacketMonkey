package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.GroupBean
import com.android.ql.lf.redpacketmonkey.present.RedPacketManager
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import kotlinx.android.synthetic.main.fragment_group_setting_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class GroupSettingFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_group_setting_layout


    private val groupInfo by lazy {
        arguments!!.getSerializable("groupInfo") as GroupBean
    }

    override fun initView(view: View?) {
        mTvGroupSettingMemberCount.setOnClickListener {
            FragmentContainerActivity.from(mContext)
                    .setClazz(GroupMemberListFragment::class.java)
                    .setTitle("成员列表")
                    .setExtraBundle(bundleOf(Pair("groupId", groupInfo.group_id)))
                    .setNeedNetWorking(true)
                    .start()
        }
        mTvGroupSettingLogoutGroup.setOnClickListener {
            alert("提示", "确定要退出群组吗？", "退出", "不退出", { _, _ ->
                RxBus.getDefault().post(GroupLogoutBean())
                finish()
            }, null)
        }
        mTvGroupSettingClearAllMessage.setOnClickListener {
            alert("提示", "确定要清空聊天记录吗？", "清空", "不清空", { _, _ ->
                RedPacketManager.deleteRedPacketAll(groupInfo.group_id!!)
                toast("清空成功")
            }, null)
        }
        mCbGroupSettingPingbiMessage.isEnabled = false
        mCbGroupSettingPingbiMessage.buttonTintMode = PorterDuff.Mode.SRC_ATOP
        mCbGroupSettingPingbiMessage.isChecked = RedPacketManager.isBlockMessage(mContext, groupInfo.group_id!!)
        mRlGroupSettingPingbiMessage.setOnClickListener {
            if (!mCbGroupSettingPingbiMessage.isChecked) {
                alert("提示", "确定要屏蔽群消息吗？", "屏蔽", "不屏蔽", { _, _ ->
                    PreferenceUtils.setPrefString(mContext, "block_group_id", PreferenceUtils.getPrefString(mContext, "block_group_id", "") + groupInfo.group_id + ",")
                    Log.e("TAG", PreferenceUtils.getPrefString(mContext, "block_group_id", ""))
                    mCbGroupSettingPingbiMessage.isChecked = true
                    toast("屏蔽成功")
                }, null)
            } else {
                alert("提示", "确定要取消屏蔽群消息吗？", "取消", "不取消", { _, _ ->
                    var blockStr = PreferenceUtils.getPrefString(mContext, "block_group_id", "")
                    blockStr = blockStr.replace(groupInfo.group_id.toString() + ",", "")
                    PreferenceUtils.setPrefString(mContext, "block_group_id", blockStr)
                    mCbGroupSettingPingbiMessage.isChecked = false
                    toast("取消成功")
                }, null)
            }
        }
        mPresent.getDataByPost(0x0, RequestParamsHelper.getGroupSettingParam(groupInfo.group_id.toString()))
    }

    override fun onRequestStart(requestID: Int) {
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在加载群信息……")
            }
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        when (requestID) {
            0x0 -> {
                val checked = checkResultCode(result)
                if (checked != null) {
                    if (checked.code == SUCCESS_CODE) {
                        val resultJson = checked.obj as JSONObject
                        mTvGroupSettingMemberCount.text = "共${resultJson.optString("count")}人"
                        mTvGroupSettingGroupNum.text = resultJson.optString("number")
                        val dataJsonArray = resultJson.optJSONArray("data")
                        if (dataJsonArray != null && dataJsonArray.length() > 0) {
                            (0 until dataJsonArray.length()).forEach {
                                val childView = View.inflate(mContext, R.layout.adapter_group_member_item_layout, null) as LinearLayout
                                val param = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
                                param.weight = 1.0f
                                param.width = 0
                                childView.layoutParams = param
                                GlideManager.loadFaceCircleImage(mContext, dataJsonArray.optJSONObject(it).optString("group_user_pic"), childView.findViewById(R.id.mIvGroupSettingMemberItemPic))
                                childView.findViewById<TextView>(R.id.mTvGroupSettingMemberItemNickName).text = dataJsonArray.optJSONObject(it).optString("group_user_name")
                                mLlGroupSettingMemberContainer.addView(childView)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> "加载失败……"
            else -> "未知错误"
        }
    }

    class GroupLogoutBean

}