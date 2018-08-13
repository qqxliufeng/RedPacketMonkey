package com.android.ql.lf.redpacketmonkey.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import com.android.ql.lf.redpacketmonkey.utils.isMoney
import org.jetbrains.anko.support.v4.toast

class CrashFragment : DialogFragment() {

    private var listener: (() -> Unit)? = null

    private var selectTypeListener: (() -> Unit)? = null

    private var selectBankCarListener: (() -> Unit)? = null

    private var tv_select_type: TextView? = null

    private var tv_bank_card: TextView? = null

    private var ll_bank_card_container: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        val contentView = inflater.inflate(R.layout.dialog_crash_layout, container, false)
        val et_money = contentView.findViewById<EditText>(R.id.mEtCrashDialogMoney)
        ll_bank_card_container = contentView.findViewById(R.id.mLlCrashDialogBankCardContainer)
        tv_bank_card = contentView.findViewById(R.id.mTvCrashDialogBankCard)
        et_money.setText("")
        contentView.findViewById<ImageView>(R.id.mIvCrashDialogClose).setOnClickListener {
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.mTvCrashDialogAllCrash).setOnClickListener {
            et_money.setText(UserInfo.getInstance().money_sum_cou)
        }
        contentView.findViewById<Button>(R.id.mBtCrashDialogSubmit).setOnClickListener {
            if (et_money.isEmpty()) {
                toast("请输入提现金额")
                return@setOnClickListener
            }
            if (!et_money.isMoney()) {
                toast("请输入合法的提现金额")
                return@setOnClickListener
            }
            listener?.invoke()
        }
        tv_select_type = contentView.findViewById(R.id.mTvCrashDialogSelectType)
        tv_select_type?.setOnClickListener {
            selectTypeListener?.invoke()
        }
        ll_bank_card_container?.setOnClickListener {
            selectBankCarListener?.invoke()
        }
        contentView.findViewById<TextView>(R.id.mTvCrashDialogAllMoney).text = "可用余额：￥${UserInfo.getInstance().money_sum_cou}"
        return contentView
    }

    fun setSelectedTypeResult(type: String) {
        tv_select_type?.text = type
        if (type == "支付宝") {
            ll_bank_card_container?.visibility = View.GONE
        } else {
            ll_bank_card_container?.visibility = View.VISIBLE
        }
    }

    fun setBankCardName(bankCardName: String) {
        tv_bank_card?.text = bankCardName
    }


    fun myShow(manager: FragmentManager?, tag: String?, listener: () -> Unit, selectTypeListener: () -> Unit, selectBankCardListener: () -> Unit) {
        this.listener = listener
        this.selectBankCarListener = selectBankCardListener
        this.selectTypeListener = selectTypeListener
        super.show(manager, tag)
    }
}