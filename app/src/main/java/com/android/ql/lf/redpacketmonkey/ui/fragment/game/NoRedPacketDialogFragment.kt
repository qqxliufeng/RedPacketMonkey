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
import com.android.ql.lf.redpacketmonkey.utils.GlideManager

class NoRedPacketDialogFragment : DialogFragment() {

    private var faceUrl: String? = null
    private var nickName: String? = null
    private var listener: (() -> Unit)? = null

    private var tv_tip: TextView? = null
    private var tip: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = inflater.inflate(R.layout.dialog_no_red_packet_layout, container, false)
        val iv_face = contentView.findViewById<ImageView>(R.id.mIvNoRedPacketFace)
        val tv_nick_name = contentView.findViewById<TextView>(R.id.mTvNoRedPacketNickName)
        tv_tip = contentView.findViewById(R.id.mTvNoRedPacketTip)
        tv_tip?.text = tip
        if (faceUrl != null) {
            GlideManager.loadFaceCircleImage(context, faceUrl, iv_face)
        }
        if (nickName != null) {
            tv_nick_name.text = nickName
        }
        contentView.findViewById<TextView>(R.id.mTvNoRedPacketSeeAllInfo).setOnClickListener {
            listener?.invoke()
            dismiss()
        }
        contentView.findViewById<ImageView>(R.id.mIvNoRedPacketClose).setOnClickListener {
            dismiss()
        }
        return contentView
    }


    fun myShow(manager: FragmentManager?, tag: String?, faceUrl: String?, nickName: String, tip: String, listener: (() -> Unit)?) {
        this.faceUrl = faceUrl
        this.nickName = nickName
        this.listener = listener
        this.tip = tip
        super.show(manager, tag)
    }
}