package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_login_layout.*

class LoginFragment : BaseNetWorkingFragment() {

    companion object {
        fun startLogin(context: Context) {
            FragmentContainerActivity.from(context).setClazz(LoginFragment::class.java).setTitle("登录").setHiddenToolBar(true).setNeedNetWorking(true).start()
        }
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
    }

}