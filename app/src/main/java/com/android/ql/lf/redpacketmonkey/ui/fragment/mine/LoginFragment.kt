package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.present.UserPresent
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.activity.MainActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.getTextString
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import com.android.ql.lf.redpacketmonkey.utils.isPhone
import kotlinx.android.synthetic.main.fragment_login_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class LoginFragment : BaseNetWorkingFragment() {

    companion object {
        fun startLogin(context: Context) {
            FragmentContainerActivity.from(context).setClazz(LoginFragment::class.java).setTitle("登录").setHiddenToolBar(true).setNeedNetWorking(true).start()
        }
    }

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_login_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlLogin.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight

        mIvLoginBack.setOnClickListener {
            finish()
        }

        mTvLoginForgetPassword.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(ForgetPasswordFragment::class.java).setHiddenToolBar(true).setNeedNetWorking(true).start()
        }
        mTvLoginRegister.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(RegisterFragment::class.java).setHiddenToolBar(true).setNeedNetWorking(true).start()
        }

        mBtLogin.setOnClickListener {
            if (mEtLoginPhone.isEmpty()) {
                toast("请输入手机号")
                return@setOnClickListener
            }
            if (!mEtLoginPhone.isPhone()) {
                toast("请输入正确的手机号")
                return@setOnClickListener
            }
            if (mEtLoginPassword.isEmpty()) {
                toast("请输入密码")
                return@setOnClickListener
            }
            if (mEtLoginPassword.getTextString().length < 6) {
                toast("密码长度至少6位")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.getLoginParams(mEtLoginPhone.getTextString(), mEtLoginPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        getFastProgressDialog("正在登录……")
    }

    override fun showFailMessage(requestID: Int): String {
        return "登录失败"
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        if (checkedObjType(obj)) {
            userPresent.onLogin(obj as JSONObject)
            startActivity(Intent(mContext, MainActivity::class.java))
            finish()
        }
    }

}