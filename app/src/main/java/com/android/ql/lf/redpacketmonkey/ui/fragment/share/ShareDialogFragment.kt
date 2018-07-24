package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R

class ShareDialogFragment :BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_share_dialog_layout,container,false)
        contentView.findViewById<TextView>(R.id.mTvShareDialogWX).setOnClickListener { dismiss() }
        contentView.findViewById<TextView>(R.id.mTvShareDialogCircle).setOnClickListener { dismiss() }
        contentView.findViewById<TextView>(R.id.mTvShareDialogQQ).setOnClickListener { dismiss() }
        contentView.findViewById<TextView>(R.id.mTvShareDialogQQZone).setOnClickListener { dismiss() }
        contentView.findViewById<TextView>(R.id.mTvShareDialogCancel).setOnClickListener { dismiss() }
        return contentView
    }

}