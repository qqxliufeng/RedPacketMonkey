package com.android.ql.lf.redpacketmonkey.ui.fragment.setting

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.PHONE_REG
import com.android.ql.lf.redpacketmonkey.utils.PreferenceUtils
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import kotlinx.android.synthetic.main.fragment_sound_setting_layout.*
import org.jetbrains.anko.support.v4.toast

class SoundSettingFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_sound_setting_layout

    override fun initView(view: View?) {
        mSwSoundSettingNewMessage.isChecked = UserInfo.getInstance().user_is_news == 1
        mSwSoundSettingRedPacket.isChecked = UserInfo.getInstance().user_is_red == 1
        PreferenceUtils.setPrefBoolean(mContext,"new_message_sound",UserInfo.getInstance().user_is_news == 1)
        PreferenceUtils.setPrefBoolean(mContext,"red_packet_sound",UserInfo.getInstance().user_is_red == 1)
        mSwSoundSettingNewMessage.setOnClickListener {
            mPresent.getDataByPost(0x0, RequestParamsHelper.getSoundSwitchParam(1, if (UserInfo.getInstance().user_is_news == 1) 2 else 1))
        }
        mSwSoundSettingRedPacket.setOnClickListener {
            mPresent.getDataByPost(0x1, RequestParamsHelper.getSoundSwitchParam(0, if (UserInfo.getInstance().user_is_red == 1) 2 else 1))
        }
    }


    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在设置消息通知")
            }
            0x1 -> {
                getFastProgressDialog("正在设置红包音效")
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x0 -> {
                if (obj != null) {
                    toast("修改成功")
                    UserInfo.getInstance().user_is_news = if (UserInfo.getInstance().user_is_news == 1) 2 else 1
                    mSwSoundSettingNewMessage.isChecked = UserInfo.getInstance().user_is_news == 1
                    PreferenceUtils.setPrefBoolean(mContext,"new_message_sound",UserInfo.getInstance().user_is_news == 1)
                }
            }
            0x1 -> {
                if (obj != null) {
                    toast("修改成功")
                    UserInfo.getInstance().user_is_red = if (UserInfo.getInstance().user_is_red == 1) 2 else 1
                    mSwSoundSettingRedPacket.isChecked = UserInfo.getInstance().user_is_red == 1
                    PreferenceUtils.setPrefBoolean(mContext,"red_packet_sound",UserInfo.getInstance().user_is_red == 1)
                }
            }
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        when (requestID) {
            0x0 -> {
                mSwSoundSettingNewMessage.isChecked = UserInfo.getInstance().user_is_news == 1
                PreferenceUtils.setPrefBoolean(mContext,"new_message_sound",UserInfo.getInstance().user_is_news == 1)
            }
            0x1 -> {
                mSwSoundSettingRedPacket.isChecked = UserInfo.getInstance().user_is_red == 1
                PreferenceUtils.setPrefBoolean(mContext,"red_packet_sound",UserInfo.getInstance().user_is_red == 1)
            }
            else -> {
            }
        }
    }


    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> {
                "消息通知设置失败"
            }
            0x1 -> {
                "红包音效设置失败"
            }
            else -> {
                "未知错误"
            }
        }
    }
}