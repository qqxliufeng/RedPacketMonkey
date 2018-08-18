package com.android.ql.lf.redpacketmonkey.ui.fragment.setting

import android.os.CountDownTimer
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.livedata.UserInfoLiveData
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import kotlinx.android.synthetic.main.fragment_pay_password_layout.*
import org.jetbrains.anko.support.v4.toast

class PayPasswordFragment : BaseNetWorkingFragment() {

    private val countDownTimer by lazy {
        object : CountDownTimer(Constants.MAX_COUNT_DOWN, 1000) {
            override fun onFinish() {
                mTvSettingPayCode.text = "重新发送？"
                mTvSettingPayCode.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mTvSettingPayCode.text = "剩余${millisUntilFinished / 1000}秒"
            }
        }
    }

    private var code: String? = null

    override fun getLayoutId() = R.layout.fragment_pay_password_layout

    override fun initView(view: View?) {
        mTvSettingPayCode.setOnClickListener {
            if (mEtSettingPayPhone.isEmpty()) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            if (!mEtSettingPayPhone.isPhone()) {
                toast("请输入正确的手机号")
                return@setOnClickListener
            }
            countDownTimer.start()
            mPresent.getDataByPost(0x1, RequestParamsHelper.getPhoneParam(mEtSettingPayPhone.getTextString()))
        }

        mBtPayPasswordSubmit.setOnClickListener {
            if (mTvSettingPayPassword.isEmpty()) {
                toast("请输入密码")
                return@setOnClickListener
            }
            if (mTvSettingPayPassword.getTextString().length < 6) {
                toast("密码长度至少6位")
                return@setOnClickListener
            }
            if (mTvSettingPayConfirmPassword.isEmpty()) {
                toast("请再次输入密码")
                return@setOnClickListener
            }
            if (mTvSettingPayConfirmPassword.getTextString().length < 6) {
                toast("密码长度至少6位")
                return@setOnClickListener
            }
            if (mTvSettingPayPassword.getTextString() != mTvSettingPayConfirmPassword.getTextString()) {
                toast("两次密码不一致")
                return@setOnClickListener
            }
            if (mEtSettingPayCode.isEmpty()){
                toast("请输入验证码")
                return@setOnClickListener
            }
            if (code != mEtSettingPayCode.getTextString()){
                toast("请输入正确的验证码")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.getPayPasswordParam(mTvSettingPayPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在修改支付密码")
            }
            0x1 -> {
                getFastProgressDialog("正在获取短信验证码")
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> {
                "支付密码修改失败"
            }
            0x1 -> {
                "验证码获取失败"
            }
            else -> {
                ""
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x0 -> {
                toast("密码修改成功，请牢记")
                UserInfoLiveData.postUserInfo()
                finish()
            }
            0x1 -> {
                if (obj != null && obj is Int) {
                    code = obj.toString()
                    toast("验证码发送成功，请注意查收！")
                }
            }
        }
    }

    override fun onDestroyView() {
        countDownTimer.cancel()
        super.onDestroyView()
    }
}