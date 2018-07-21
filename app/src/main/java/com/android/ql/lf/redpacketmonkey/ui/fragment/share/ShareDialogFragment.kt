package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.redpacketmonkey.R

class ShareDialogFragment :BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_share_dialog_layout,container,false)
        return contentView
    }

}