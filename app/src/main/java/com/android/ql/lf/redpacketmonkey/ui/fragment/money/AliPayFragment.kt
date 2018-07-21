package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_ali_pay_layout.*

class AliPayFragment :BaseNetWorkingFragment(){

    private val aliPayNameDialog by lazy {
        AliPayNameFragment()
    }

    override fun getLayoutId() = R.layout.fragment_ali_pay_layout

    override fun initView(view: View?) {
        mRlAliPayNameContainer.setOnClickListener {
            aliPayNameDialog.show(childFragmentManager,"ali_pay_name_dialog")
        }
        mRlAliPayAccountContainer.setOnClickListener {

        }
    }


}