package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.os.CountDownTimer
import android.view.View
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.BankCardInfoBean
import com.android.ql.lf.redpacketmonkey.data.PostBankCardBean
import com.android.ql.lf.redpacketmonkey.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.lf.redpacketmonkey.utils.*
import kotlinx.android.synthetic.main.fragment_add_bank_car_layout.*
import org.jetbrains.anko.support.v4.toast

class AddBankCarFragment : BaseNetWorkingFragment() {


    private val countDownTimer by lazy {
        object : CountDownTimer(Constants.MAX_COUNT_DOWN, 1000) {
            override fun onFinish() {
                mTvAddBankListCardGetCode.text = "重新发送？"
                mTvAddBankListCardGetCode.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mTvAddBankListCardGetCode.text = "剩余${millisUntilFinished / 1000}秒"
            }
        }
    }

    private val postBankCardBean by lazy {
        PostBankCardBean()
    }

    private var code: String? = null

    override fun getLayoutId() = R.layout.fragment_add_bank_car_layout

    override fun initView(view: View?) {
        mTvAddBankListCardGetCode.setOnClickListener {
            if (mEtAddBankListCardPhone.isEmpty()) {
                toast("手机号不能为空")
                return@setOnClickListener
            }
            if (!mEtAddBankListCardPhone.isPhone()) {
                toast("请输入合法的手机号")
                return@setOnClickListener
            }
            countDownTimer.start()
            mPresent.getDataByPost(0x0, RequestParamsHelper.getPhoneParam(mEtAddBankListCardPhone.getTextString()))
        }
        mBtAddBankListCardSubmit.setOnClickListener {
            postBankCardBean.name = mEtAddBankListCardName.getTextString()
            postBankCardBean.cardNumber = mEtAddBankListCardNumber.getTextString()
            postBankCardBean.idCardNumber = mEtAddBankListCardIdCard.getTextString()
            postBankCardBean.phone = mEtAddBankListCardPhone.getTextString()
            postBankCardBean.code = mEtAddBankListCardCode.getTextString()
            val checked = postBankCardBean.checked()
            if (checked != "") {
                toast(checked)
                return@setOnClickListener
            }
            if (postBankCardBean.code != code) {
                toast("请输入正确的验证码")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x1, RequestParamsHelper.getAddBankCarParam(postBankCardBean))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x0 -> {
                getFastProgressDialog("正在发送验证码……")
            }
            0x1 -> {
                getFastProgressDialog("正在添加……")
            }
        }
    }

    override fun showFailMessage(requestID: Int): String {
        return when (requestID) {
            0x0 -> {
                "获取验证码失败"
            }
            0x1 -> {
                "添加失败"
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
                toast("添加成功")
                RxBus.getDefault().post(BankCardInfoBean())
                finish()
            }
        }
    }


    override fun onDestroyView() {
        countDownTimer.cancel()
        super.onDestroyView()
    }

}