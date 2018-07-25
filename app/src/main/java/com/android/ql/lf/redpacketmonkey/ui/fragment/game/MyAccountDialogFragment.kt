package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R


class MyAccountDialogFragment : DialogFragment() {

    private var account: String = "0.0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = inflater.inflate(R.layout.dialog_my_account_layout, container, false)
        contentView.findViewById<Button>(R.id.mBtMyAccountSubmit).setOnClickListener { dismiss() }
        contentView.findViewById<TextView>(R.id.mTvMyAccountMoney).text = "账户余额：￥$account"
        return contentView
    }


    fun myShow(manager: FragmentManager?, tag: String?, account: String) {
        this.account = account
        super.show(manager, tag)
    }

}