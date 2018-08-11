package com.android.ql.lf.redpacketmonkey.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R
import com.android.ql.lf.redpacketmonkey.data.UserInfo
import com.android.ql.lf.redpacketmonkey.utils.isEmpty
import com.android.ql.lf.redpacketmonkey.utils.isMoney
import org.jetbrains.anko.support.v4.toast

class CrashFragment : DialogFragment() {

    private var listener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        val contentView = inflater.inflate(R.layout.dialog_crash_layout, container, false)
        val et_money = contentView.findViewById<EditText>(R.id.mEtCrashDialogMoney)

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
        contentView.findViewById<TextView>(R.id.mTvCrashDialogAllMoney).text = "可用余额：￥${UserInfo.getInstance().money_sum_cou}"
        return contentView
    }


    fun myShow(manager: FragmentManager?, tag: String?, listener: () -> Unit) {
        this.listener = listener
        super.show(manager, tag)
    }
}