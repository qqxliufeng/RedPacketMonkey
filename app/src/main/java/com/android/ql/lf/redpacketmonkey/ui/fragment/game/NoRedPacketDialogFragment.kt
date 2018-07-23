package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R

class NoRedPacketDialogFragment : DialogFragment() {

    private var listener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = inflater.inflate(R.layout.dialog_no_red_packet_layout, container, false)
        contentView.findViewById<TextView>(R.id.mTvNoRedPacketSeeAllInfo).setOnClickListener {
            listener?.invoke()
            dismiss()
        }
        contentView.findViewById<ImageView>(R.id.mIvNoRedPacketClose).setOnClickListener {
            dismiss()
        }
        return contentView
    }


    fun myShow(manager: FragmentManager?, tag: String?, listener: (() -> Unit)?) {
        this.listener = listener
        super.show(manager, tag)
    }
}