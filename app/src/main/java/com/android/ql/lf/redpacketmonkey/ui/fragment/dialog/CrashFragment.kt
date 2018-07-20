package com.android.ql.lf.redpacketmonkey.ui.fragment.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.ql.lf.redpacketmonkey.R

class CrashFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.dialog_crash_layout, container, false)
        contentView.findViewById<ImageView>(R.id.mIvCrashDialogClose).setOnClickListener {
            dismiss()
        }
        return contentView
    }

}