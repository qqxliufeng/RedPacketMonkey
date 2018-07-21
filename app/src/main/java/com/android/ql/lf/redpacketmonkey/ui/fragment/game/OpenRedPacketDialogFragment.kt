package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.redpacketmonkey.R

class OpenRedPacketDialogFragment : DialogFragment() {

    private var animationDrawable: AnimationDrawable? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = inflater.inflate(R.layout.dialog_open_red_packet_layout, container, false)
        val iv_open = contentView.findViewById<ImageView>(R.id.mIvOpenRedPacketOpenAnim)
        contentView.findViewById<TextView>(R.id.mTvOpenRedPacketOpen).setOnClickListener {
            it.visibility = View.GONE
            iv_open.visibility = View.VISIBLE
            animationDrawable = iv_open.drawable as AnimationDrawable
            animationDrawable?.start()
        }
        contentView.findViewById<ImageView>(R.id.mIvOpenRedPacketClose).setOnClickListener {
            dismiss()
        }
        return contentView
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        animationDrawable?.stop()
    }

}