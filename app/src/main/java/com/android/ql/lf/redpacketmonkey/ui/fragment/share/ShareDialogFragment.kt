package com.android.ql.lf.redpacketmonkey.ui.fragment.share

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R

class ShareDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val WX_SHARE = 0
        const val CIRCLE_SHARE = 1
        const val QQ_SHARE = 2
        const val ZONE_SHARE = 3
    }

    private var listener: ((Int) -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_share_dialog_layout, container, false)
        contentView.findViewById<TextView>(R.id.mTvShareDialogWX).setOnClickListener {
            listener?.invoke(WX_SHARE)
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.mTvShareDialogCircle).setOnClickListener {
            listener?.invoke(CIRCLE_SHARE)
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.mTvShareDialogQQ).setOnClickListener {
            listener?.invoke(QQ_SHARE)
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.mTvShareDialogQQZone).setOnClickListener {
            listener?.invoke(ZONE_SHARE)
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.mTvShareDialogCancel).setOnClickListener {
            dismiss()
        }
        return contentView
    }

    fun myShow(manager: FragmentManager?, tag: String?, listener: (Int) -> Unit) {
        this.listener = listener
        super.show(manager, tag)
    }

}