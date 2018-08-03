package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import kotlinx.android.synthetic.main.fragment_forget_pasword_layout.*
import org.jetbrains.anko.support.v4.toast

class ForgetPasswordFragment : BaseNetWorkingFragment() {

    private val countDownTimer by lazy {
        object : CountDownTimer(Constants.MAX_COUNT_DOWN, 1000) {
            override fun onFinish() {
                mTvForgetPasswordCode.text = "重新发送？"
                mTvForgetPasswordCode.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mTvForgetPasswordCode.text = "剩余${millisUntilFinished / 1000}秒"
            }
        }
    }

    private var code: String? = null

    override fun getLayoutId() = R.layout.fragment_forget_pasword_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlForgetPassword.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mIvForgetPasswordBack.setOnClickListener {
            finish()
        }
        mTvForgetPasswordCode.setOnClickListener {
            if (mEtForgetPasswordPhone.isEmpty()) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            if (!mEtForgetPasswordPhone.isPhone()) {
                toast("请输入合法的手机号")
                return@setOnClickListener
            }
            countDownTimer.start()
            mPresent.getDataByPost(0x0, RequestParamsHelper.getPhoneParam(mEtForgetPasswordPhone.getTextString()))
        }
        mBtForgetPassword.setOnClickListener {
            if (mEtForgetPasswordPhone.isEmpty()) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            if (!mEtForgetPasswordPhone.isPhone()) {
                toast("请输入合法的手机号")
                return@setOnClickListener
            }
            if (mEtForgetPasswordCode.isEmpty()) {
                toast("请输入短信验证码")
                return@setOnClickListener
            }
            if (mEtForgetPasswordCode.getTextString() != code) {
                toast("请输入正确的验证码")
                return@setOnClickListener
            }
            if (mEtForgetPasswordPassword.isEmpty()) {
                toast("请输入密码")
                return@setOnClickListener
            }
            if (mEtForgetPasswordPassword.getTextString().length < 6) {
                toast("密码长度至少6位")
                return@setOnClickListener
            }
            if (mEtForgetPasswordConfirmPassword.isEmpty()) {
                toast("请再次输入密码")
                return@setOnClickListener
            }
            if (mEtForgetPasswordConfirmPassword.getTextString().length < 6) {
                toast("密码长度至少6位")
                return@setOnClickListener
            }
            if (mEtForgetPasswordPassword.getTextString() != mEtForgetPasswordConfirmPassword.getTextString()) {
                toast("两次密码不一致")
                mEtForgetPasswordPassword.setText("")
                mEtForgetPasswordConfirmPassword.setText("")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x1, RequestParamsHelper.getForgetPWParams(
                    mEtForgetPasswordPhone.getTextString(),
                    mEtForgetPasswordPassword.getTextString(),
                    mEtForgetPasswordConfirmPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在发送验证码……")
            }
            0x1 -> {
                getFastProgressDialog("正在修改……")
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> {
                "获取验证码失败"
            }
            0x1 -> {
                "修改失败"
            }
            else -> {
                "未知错误"
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        when (requestID) {
            0x0 -> {
                if (obj != null && obj is Int) {
                    code = obj.toString()
                    toast("验证码发送成功，请注意查收！")
                }
            }
            0x1 -> {
                if (obj != null && obj is String) {
                    toast("修改成功，请牢记！")
                    finish()
                }
            }
        }
    }

    override fun onDestroyView() {
        countDownTimer.cancel()
        super.onDestroyView()
    }
}