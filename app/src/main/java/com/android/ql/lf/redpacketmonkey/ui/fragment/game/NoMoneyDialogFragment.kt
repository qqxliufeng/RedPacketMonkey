package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.ql.lf.redpacketmonkey.R

class NoMoneyDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = inflater.inflate(R.layout.dialog_no_money_layout, container, false)
        contentView.findViewById<Button>(R.id.mBtNoMoney).setOnClickListener {
            dismiss()
        }
        return contentView
    }
}