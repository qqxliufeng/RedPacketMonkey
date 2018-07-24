package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_ali_pay_layout.*

class AliPayFragment : BaseNetWorkingFragment() {

    private val aliPayNameDialog by lazy {
        AliPayNameDialogFragment()
    }

    private val aliPayAccountDialog by lazy {
        AliPayNameDialogFragment()
    }

    override fun getLayoutId() = R.layout.fragment_ali_pay_layout

    override fun initView(view: View?) {
        mRlAliPayNameContainer.setOnClickListener {
            aliPayNameDialog.myShow(childFragmentManager, "ali_pay_name_dialog") {
                mTvAliPayName.text = it
            }
        }
        mRlAliPayAccountContainer.setOnClickListener {
            aliPayAccountDialog.myShow(childFragmentManager, "ali_pay_account_dialog", "修改支付宝", "账号") {
                mTvAliPayAccount.text = it
            }
        }
    }

}