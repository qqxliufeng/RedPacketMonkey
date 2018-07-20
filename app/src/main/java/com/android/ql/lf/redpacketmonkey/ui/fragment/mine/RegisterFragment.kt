package com.android.ql.lf.redpacketmonkey.ui.fragment.mine

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.activity.FragmentContainerActivity
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_register_layout.*

class RegisterFragment : BaseNetWorkingFragment() {


    override fun getLayoutId() = R.layout.fragment_register_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.TRANSPARENT)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        (mTlRegister.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mIvRegisterBack.setOnClickListener {
            finish()
        }
    }
}