package com.android.ql.lf.redpacketmonkey.ui.fragment.money

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R

class AliPayNameFragment :DialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.dialog_ali_pay_name_layout,container,false)
        return contentView
    }


}