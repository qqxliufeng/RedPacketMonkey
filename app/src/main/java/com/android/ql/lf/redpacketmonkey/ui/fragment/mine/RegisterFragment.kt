package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.RequestCallback
import cn.jpush.im.android.api.model.DeviceInfo
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import kotlinx.android.synthetic.main.fragment_register_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class RegisterFragment : BaseNetWorkingFragment() {


    private val countDownTimer by lazy {
        object : CountDownTimer(Constants.MAX_COUNT_DOWN, 1000) {
            override fun onFinish() {
                mTvRegisterCode.text = "重新发送？"
                mTvRegisterCode.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mTvRegisterCode.text = "剩余${millisUntilFinished / 1000}秒"
            }
        }
    }

    private var code: String? = null

    override fun getLayoutId() = R.layout.fragment_register_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlRegister.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mIvRegisterBack.setOnClickListener {
            finish()
        }
        mTvRegisterCode.setOnClickListener {
            if (mEtRegisterPhone.isEmpty()) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            if (!mEtRegisterPhone.isPhone()) {
                toast("请输入合法的手机号")
                return@setOnClickListener
            }
            it.isEnabled = false
            countDownTimer.start()
            mPresent.getDataByPost(0x0, RequestParamsHelper.getPhoneParam(mEtRegisterPhone.getTextString()))
        }
        mBtRegister.setOnClickListener {
            if (mEtRegisterPhone.isEmpty()) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            if (!mEtRegisterPhone.isPhone()) {
                toast("请输入合法的手机号")
                return@setOnClickListener
            }
            if (mEtRegisterNickName.isEmpty()) {
                toast("请输入昵称")
                return@setOnClickListener
            }
            if (mEtRegisterPassword.isEmpty()) {
                toast("请输入密码")
                return@setOnClickListener
            }
            if (mEtRegisterPassword.getTextString().length < 6) {
                toast("密码长度至少6位")
                return@setOnClickListener
            }
            if (mEtRegisterCode.isEmpty()) {
                toast("请输入短信验证码")
                return@setOnClickListener
            }
            if (mEtRegisterCode.getTextString() != code) {
                toast("请输入正确的验证码")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x1, RequestParamsHelper.getRegisterParams(mEtRegisterNickName.getTextString(), mEtRegisterPhone.getTextString(), mEtRegisterPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在发送验证码……")
            }
            0x1 -> {
                getFastProgressDialog("正在注册……")
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> {
                "获取验证码失败"
            }
            0x1 -> {
                "注册失败"
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
                if (checkedObjType(obj)) {
                    JMessageClient.register((obj as JSONObject).optString("user_as"), obj.optString("user_as"), object : RequestCallback<List<DeviceInfo>>() {
                        override fun gotResult(p0: Int, p1: String?, p2: List<DeviceInfo>?) {
                            if (progressDialog != null && progressDialog.isShowing) {
                                progressDialog.dismiss()
                                progressDialog = null
                            }
                            if (p0 == 0) { //表示注册成功
                                toast("注册成功，请登录！")
                                finish()
                            } else { //表示注册异常，提示异常信息
                                toast("注册失败，请重试！")
                            }
                        }
                    })

                }
            }
        }
    }

    override fun onDestroyView() {
        countDownTimer.cancel()
        super.onDestroyView()
    }
}