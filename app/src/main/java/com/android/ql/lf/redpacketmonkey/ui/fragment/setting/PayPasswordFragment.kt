package com.android.ql.lf.redpacketmonkey.ui.fragment.setting

import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.RequestParamsHelper
import com.android.ql.lf.redpacketmonkey.utils.getTextString
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import kotlinx.android.synthetic.main.fragment_pay_password_layout.*
import org.jetbrains.anko.support.v4.toast

class PayPasswordFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_pay_password_layout

    override fun initView(view: View?) {
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
            if (mTvSettingPayPassword.getTextString()!=mTvSettingPayConfirmPassword.getTextString()){
                toast("两次密码不一致")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.getPayPasswordParam(mTvSettingPayPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在修改支付密码")
    }

    override fun showFailMessage(requestID: Int): String {
        return "支付密码修改失败"
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        if (obj != null) {
            toast("密码修改成功，请牢记")
            finish()
        }
    }


}