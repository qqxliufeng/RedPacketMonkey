package com.android.ql.lf.redpacketmonkey.ui.fragment.setting

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseFragment
import com.android.ql.lf.redpacketmonkey.utils.VersionHelp
import kotlinx.android.synthetic.main.fragment_setting_layout.*

class SettingFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_setting_layout

    override fun initView(view: View?) {
        mTvSettingVersion.text = "当前版本${VersionHelp.currentVersionName(mContext)}"
        mTvSettingResetPassword.setOnClickListener {

        }
    }
}