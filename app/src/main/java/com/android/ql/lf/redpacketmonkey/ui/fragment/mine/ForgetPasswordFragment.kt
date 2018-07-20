package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_forget_pasword_layout.*

class ForgetPasswordFragment :BaseNetWorkingFragment(){


    override fun getLayoutId() = R.layout.fragment_forget_pasword_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlForgetPassword.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mIvForgetPasswordBack.setOnClickListener {
            finish()
        }
    }
}