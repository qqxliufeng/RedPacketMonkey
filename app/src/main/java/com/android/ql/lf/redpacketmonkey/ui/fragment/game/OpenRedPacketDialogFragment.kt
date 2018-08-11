package com.android.ql.lf.redpacketmonkey.ui.fragment.game

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
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
import org.jetbrains.anko.support.v4.toast

class OpenRedPacketDialogFragment : DialogFragment() {

    private var animationDrawable: AnimationDrawable? = null

    private var faceUrl: String? = null
    private var nickName: String? = null
    private var count: String? = null
    private var listener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = inflater.inflate(R.layout.dialog_open_red_packet_layout, container, false)
        val iv_face = contentView.findViewById<ImageView>(R.id.mIvOpenRedPacketFacePic)
        val tv_nick_name = contentView.findViewById<TextView>(R.id.mTvOpenRedPacketNickName)
        val tv_count = contentView.findViewById<TextView>(R.id.mTvOpenRedPacketCount)
        if (faceUrl != null) {
            GlideManager.loadFaceCircleImage(context, faceUrl, iv_face)
        }
        if (nickName != null) {
            tv_nick_name.text = nickName
        }
        if (count != null) {
            tv_count.text = count
        }
        val iv_open = contentView.findViewById<ImageView>(R.id.mIvOpenRedPacketOpenAnim)
        contentView.findViewById<TextView>(R.id.mTvOpenRedPacketOpen).setOnClickListener {
            it.visibility = View.GONE
            iv_open.visibility = View.VISIBLE
            animationDrawable = iv_open.drawable as AnimationDrawable
            animationDrawable?.start()
            iv_open.postDelayed({
                listener?.invoke()
            }, 1000)
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

    fun myShow(manager: FragmentManager?, tag: String?, faceUrl: String, nickName: String, count: String, listener: (() -> Unit)?) {
        this.faceUrl = faceUrl
        this.nickName = nickName
        this.count = count
        this.listener = listener
        super.show(manager, tag)
    }

}